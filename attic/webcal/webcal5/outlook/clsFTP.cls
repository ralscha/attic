VERSION 1.0 CLASS
BEGIN
  MultiUse = -1  'True
END
Attribute VB_Name = "clsFTP"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = False

'FTP Class by Andrew Baker

'www.vbusers.com

'Note:  The code provided here are supplied AS IS without warranty or guarantee of any kind.
'       If you use any of the source code or applications from this site, you do so
'       AT YOUR OWN RISK

Option Explicit
Option Compare Text

Private Const FTP_TRANSFER_TYPE_UNKNOWN = &H0
Private Const FTP_TRANSFER_TYPE_ASCII = &H1
Private Const FTP_TRANSFER_TYPE_BINARY = &H2
'Default for FTP servers
Private Const INTERNET_DEFAULT_FTP_PORT = 21
Private Const INTERNET_SERVICE_FTP = 1
Private Const INTERNET_FLAG_RELOAD = &H80000000
'Use FTP connections
Private Const INTERNET_FLAG_PASSIVE = &H8000000
'Use registry configuration
Private Const INTERNET_OPEN_TYPE_PRECONFIG = 0
'Direct to net
Private Const INTERNET_OPEN_TYPE_DIRECT = 1
'Via a named proxy
Private Const INTERNET_OPEN_TYPE_PROXY = 3
'Prevent using java/script/INS
Private Const INTERNET_OPEN_TYPE_PRECONFIG_WITH_NO_AUTOPROXY = 4
Private Const MAX_PATH = 260

Private Type FILETIME
    dwLowDateTime As Long
    dwHighDateTime As Long
End Type

Private Type WIN32_FIND_DATA
    dwFileAttributes As Long
    ftCreationTime As FILETIME
    ftLastAccessTime As FILETIME
    ftLastWriteTime As FILETIME
    nFileSizeHigh As Long
    nFileSizeLow As Long
    dwReserved0 As Long
    dwReserved1 As Long
    cFileName As String * MAX_PATH
    cAlternate As String * 14
End Type

Private Declare Function InternetCloseHandle Lib "wininet.dll" (ByVal hInet As Long) As Integer
Private Declare Function InternetConnect Lib "wininet.dll" Alias "InternetConnectA" (ByVal hInternetSession As Long, ByVal sServerName As String, ByVal nServerPort As Integer, ByVal sUserName As String, ByVal sPassword As String, ByVal lService As Long, ByVal lFlags As Long, ByVal lContext As Long) As Long
Private Declare Function InternetOpen Lib "wininet.dll" Alias "InternetOpenA" (ByVal sAgent As String, ByVal lAccessType As Long, ByVal sProxyName As String, ByVal sProxyBypass As String, ByVal lFlags As Long) As Long
Private Declare Function FtpSetCurrentDirectory Lib "wininet.dll" Alias "FtpSetCurrentDirectoryA" (ByVal hFtpSession As Long, ByVal lpszDirectory As String) As Boolean
Private Declare Function FtpGetCurrentDirectory Lib "wininet.dll" Alias "FtpGetCurrentDirectoryA" (ByVal hFtpSession As Long, ByVal lpszCurrentDirectory As String, lpdwCurrentDirectory As Long) As Long
Private Declare Function FtpCreateDirectory Lib "wininet.dll" Alias "FtpCreateDirectoryA" (ByVal hFtpSession As Long, ByVal lpszDirectory As String) As Boolean
Private Declare Function FtpRemoveDirectory Lib "wininet.dll" Alias "FtpRemoveDirectoryA" (ByVal hFtpSession As Long, ByVal lpszDirectory As String) As Boolean
Private Declare Function FtpDeleteFile Lib "wininet.dll" Alias "FtpDeleteFileA" (ByVal hFtpSession As Long, ByVal lpszFileName As String) As Boolean
Private Declare Function FtpRenameFile Lib "wininet.dll" Alias "FtpRenameFileA" (ByVal hFtpSession As Long, ByVal lpszExisting As String, ByVal lpszNew As String) As Boolean
Private Declare Function FtpGetFile Lib "wininet.dll" Alias "FtpGetFileA" (ByVal hConnect As Long, ByVal lpszRemoteFile As String, ByVal lpszNewFile As String, ByVal fFailIfExists As Long, ByVal dwFlagsAndAttributes As Long, ByVal dwFlags As Long, ByRef dwContext As Long) As Boolean
Private Declare Function FtpPutFile Lib "wininet.dll" Alias "FtpPutFileA" (ByVal hConnect As Long, ByVal lpszLocalFile As String, ByVal lpszNewRemoteFile As String, ByVal dwFlags As Long, ByVal dwContext As Long) As Boolean
Private Declare Function InternetGetLastResponseInfo Lib "wininet.dll" Alias "InternetGetLastResponseInfoA" (lpdwError As Long, ByVal lpszBuffer As String, lpdwBufferLength As Long) As Boolean
Private Declare Function FtpFindFirstFile Lib "wininet.dll" Alias "FtpFindFirstFileA" (ByVal hFtpSession As Long, ByVal lpszSearchFile As String, lpFindFileData As WIN32_FIND_DATA, ByVal dwFlags As Long, ByVal dwContent As Long) As Long
Private Declare Function InternetFindNextFile Lib "wininet.dll" Alias "InternetFindNextFileA" (ByVal hFind As Long, lpvFindData As WIN32_FIND_DATA) As Long

Private zsAgent As String, zsServerName As String, zsLoginName As String, zsPassword As String
Private zbPassiveConnection As Boolean
Private zlhwndConnection As Long, zlhOpen As Long
Private zsLastError As String, zlLastErrNumber As Long

'--------Agent
Property Get Agent() As String
    Agent = zsAgent
End Property

Property Let Agent(Value As String)
    zsAgent = Value
End Property


'--------Server Name
Property Get ServerName() As String
    ServerName = zsServerName
End Property

Property Let ServerName(Value As String)
    zsServerName = Value
End Property


'--------Login Name
Property Get LoginName() As String
    LoginName = zsLoginName
End Property

Property Let LoginName(Value As String)
    zsLoginName = Value
End Property


'--------Login Password
Property Get Password() As String
    Password = zsPassword
End Property

Property Let Password(Value As String)
    zsPassword = Value
End Property

Property Get CurDir() As String
    Dim sCurPath As String * MAX_PATH, lRetVal As Long
    
    'Get the current directory
    On Error Resume Next
    lRetVal = FtpGetCurrentDirectory(zlhwndConnection, sCurPath, MAX_PATH)
    CurDir = Left$(sCurPath, InStr(1, sCurPath, vbNullChar) - 1)
    If Right$(CurDir, 1) <> "/" Then
        CurDir = CurDir & "/"
    End If
End Property

Property Let CurDir(Value As String)
    'Change the current directory
    Call FtpSetCurrentDirectory(zlhwndConnection, Value)
End Property

Function DelDir(sDirectory As String) As Boolean
    'Remove a directory in the current directory
    DelDir = FtpRemoveDirectory(zlhwndConnection, sDirectory)
End Function


Function MakeDir(sPath As String) As Boolean
    'Create a new directory in the current directory
    MakeDir = FtpCreateDirectory(zlhwndConnection, sPath)
End Function


'Purpose   :    Uploads a file from to an FTP server.
'Inputs    :    sSourceFile             The path and name of the file to upload
'Outputs   :    Returns True on success
'Author    :    Andrew Baker
'Date      :    11/09/2000
'Notes     :
'Revisions :

Function UploadFile(sSourceFile As String) As Boolean
    'Upload a file to current directory
    On Error GoTo ErrFailed
    UploadFile = FtpPutFile(zlhwndConnection, sSourceFile, zPathFileToFile(sSourceFile), FTP_TRANSFER_TYPE_UNKNOWN, 0)
    Exit Function
    
ErrFailed:
    'Store details of the error
    If Err.Number Then
        zsLastError = Err.DESCRIPTION
        zlLastErrNumber = Err.Number
    Else
        'DLL error
        zStoreError Err.LastDllError
    End If
    UploadFile = False
    On Error GoTo 0
End Function

Function RenameFile(sSourceFile As String, sNewName As String) As Boolean
    'Rename a file in current directory
    RenameFile = FtpRenameFile(zlhwndConnection, sSourceFile, sNewName)
End Function


Function Connect() As Boolean
    'Return a handle/Open an internet connection
    zlhOpen = InternetOpen(zsAgent, INTERNET_OPEN_TYPE_PRECONFIG, vbNullString, vbNullString, 0)
    'Connect to the FTP server
    If zbPassiveConnection Then
        zlhwndConnection = InternetConnect(zlhOpen, zsServerName, INTERNET_DEFAULT_FTP_PORT, zsLoginName, zsPassword, INTERNET_SERVICE_FTP, INTERNET_FLAG_PASSIVE, 0)
    Else
        zlhwndConnection = InternetConnect(zlhOpen, zsServerName, INTERNET_DEFAULT_FTP_PORT, zsLoginName, zsPassword, INTERNET_SERVICE_FTP, 0, 0)
    End If
    If zlhwndConnection Then
        Connect = True
    Else
        Connect = False
    End If
End Function

Sub Disconnect()
    'Close FTP connection
    InternetCloseHandle zlhwndConnection
    zlhwndConnection = 0
    'Close Internet connection
    InternetCloseHandle zlhOpen
    zlhOpen = 0
End Sub

Property Get FTPHwnd() As Long
    FTPHwnd = zlhwndConnection
End Property

Property Get InternetHwnd() As Long
    InternetHwnd = zlhOpen
End Property


'Purpose     :  Returns an array of files matching the sFilter string
'Inputs      :  [sFilter]                       Returns only files matching this criteria
'               [bReturnDirectories]            If False will search the files for the matching string,
'                                               else the array will return the matching directories.
'Outputs     :  asMatching                      A 1 based 1d string array.
'Author      :  Andrew Baker
'Date        :  01/02/2001 18:08
'Notes       :
'Revisions   :
'Assumptions :

Function GetMatchingFiles(ByRef asMatching() As String, Optional sFilter = "*.*", Optional bReturnDirectories As Boolean = False) As Boolean
    Dim pData As WIN32_FIND_DATA, lhwndFind As Long, lRet As Long, lMatching As Long, sThisFile As String
    Const FILE_ATTRIBUTE_READONLY = &H1, FILE_ATTRIBUTE_HIDDEN = &H2, FILE_ATTRIBUTE_SYSTEM = &H4
    Const FILE_ATTRIBUTE_DIRECTORY = &H10, FILE_ATTRIBUTE_ARCHIVE = &H20, FILE_ATTRIBUTE_NORMAL = &H80
    Const FILE_ATTRIBUTE_TEMPORARY = &H100, FILE_ATTRIBUTE_COMPRESSED = &H800, FILE_ATTRIBUTE_OFFLINE = &H1000
    
    On Error GoTo ErrFailed
    'Create a buffer
    pData.cFileName = String(MAX_PATH, 0)
    lRet = 1
    Erase asMatching
    
    'Find the first file
    lhwndFind = FtpFindFirstFile(zlhwndConnection, sFilter, pData, INTERNET_FLAG_RELOAD, 0)
    
    If lhwndFind Then
        Do
            If lRet > 0 And CBool(pData.dwFileAttributes And FILE_ATTRIBUTE_DIRECTORY) = bReturnDirectories Then
                sThisFile = Left$(pData.cFileName, InStr(1, pData.cFileName, String(1, 0), vbBinaryCompare) - 1)
                'Store the the filename
                lMatching = lMatching + 1
                If lMatching = 1 Then
                    ReDim asMatching(1 To lMatching)
                Else
                    ReDim Preserve asMatching(1 To lMatching)
                End If
                asMatching(lMatching) = Left$(pData.cFileName, InStr(1, pData.cFileName, String(1, 0), vbBinaryCompare) - 1)
            ElseIf lRet = 0 Then
                'No more matching files
                Exit Do
            End If
            'Find the next file
            lRet = InternetFindNextFile(lhwndFind, pData)
        Loop
    End If
    'Close the search
    Call InternetCloseHandle(lhwndFind)
    GetMatchingFiles = True
    Exit Function

ErrFailed:
    'Store details of the error
    If Err.Number Then
        zsLastError = Err.DESCRIPTION
        zlLastErrNumber = Err.Number
    Else
        'DLL error
        zStoreError Err.LastDllError
    End If
    DownloadFile = False
    On Error GoTo 0
End Function


'Purpose   :    Downloads a file from an FTP server.
'Inputs    :    sGetFileName            The name of the file to download from the server
'               sSaveToPath             The path to save the file to.
'               [bOverwrite]            If True overwrites will overwrite a file that may exist
'                                       at sSaveToPath. If False will abort the download if a file
'                                       exists at sSaveToPath.
'Outputs   :    Returns True on success
'Author    :    Andrew Baker
'Date      :    11/09/2000
'Notes     :
'Revisions :

Function DownloadFile(sGetFileName As String, sSaveToPath As String, Optional bOverwrite As Boolean = True) As Boolean
    On Error GoTo ErrFailed
    If Len(Dir$(sSaveToPath)) > 0 And Len(sSaveToPath) > 0 Then
        'File already exists
        If bOverwrite Then
            'Delete existing file
            Kill sSaveToPath
        Else
            'Store an error message
            zsLastError = "File already exists at " & sSaveToPath
            zlLastErrNumber = vbObjectError + 1
            DownloadFile = False
            Exit Function
        End If
    End If
    'Retrieve the file, ignoring the cache
    DownloadFile = FtpGetFile(zlhwndConnection, sGetFileName, sSaveToPath, False, 0, FTP_TRANSFER_TYPE_UNKNOWN Or INTERNET_FLAG_RELOAD, 0)
    Exit Function

ErrFailed:
    'Store details of the error
    If Err.Number Then
        zsLastError = Err.DESCRIPTION
        zlLastErrNumber = Err.Number
    Else
        'DLL error
        zStoreError Err.LastDllError
    End If
    DownloadFile = False
    On Error GoTo 0
End Function

Function DeleteFile(sFileName As String) As Boolean
    'Delete the file from server
    DeleteFile = FtpDeleteFile(zlhwndConnection, sFileName)
End Function



'Purpose   :    Convert a file and path to a path e.g. "C:\Windows\Win.ini" becomes "C:\Windows\"
'Inputs    :    sFilePathName               The path and file name to convert
'Outputs   :    Returns the file path
'Author    :    Andrew Baker
'Date      :    25/03/2000
'Notes     :    Use of InstrRev would be quicker if using for VB programmers

Private Function PathFileToPath(sFilePathName As String) As String
    Dim ThisChar As Long
    PathFileToPath = sFilePathName 'Default return value
    For ThisChar = 0 To Len(sFilePathName) - 1
        If Mid$(sFilePathName, Len(sFilePathName) - ThisChar, 1) = "\" Then
            PathFileToPath = Left$(sFilePathName, Len(sFilePathName) - ThisChar)
            Exit For
        End If
    Next
End Function


Private Sub Class_Initialize()
    zsAgent = "FTP Client"
    zbPassiveConnection = True
End Sub

Property Get PassiveConnection() As Boolean
    PassiveConnection = zbPassiveConnection
End Property

Property Let PassiveConnection(Value As Boolean)
    zbPassiveConnection = Value
End Property

'Returns the error description of the last error that occured within this class
Property Get LastError() As String
    LastError = zsLastError
End Property

'Returns the error number of the last error that occured within this class
Property Get LastErrorNumber() As Long
    LastErrorNumber = zlLastErrNumber
End Property



'Purpose   :    Stores an error in private variables
'Inputs    :    lErrNumber              The error number (see the Err object or the return value from the API call)
'Outputs   :    Returns a descriptive error message
'Author    :    Andrew Baker
'Date      :    25/03/2000
'Notes     :

Private Sub zStoreError(lErrNumber As Long)
    Dim sErr As String, lenBuf As Long
    
    'Get the size of the required buffer
    Call InternetGetLastResponseInfo(lErrNumber, sErr, lenBuf)
    'Create a buffer
    sErr = String(lenBuf, 0)
    Call InternetGetLastResponseInfo(lErrNumber, sErr, lenBuf)
    'Store the last response error
    zsLastError = sErr
    zlLastErrNumber = lErrNumber
End Sub

'Purpose   :    Convert a file and path to a file e.g. "C:\Windows\Win.ini" becomes "win.ini"
'Inputs    :    sFilePathName               The path and file name to convert
'Outputs   :    Returns the file name
'Author    :    Andrew Baker
'Date      :    25/03/2000
'Notes     :    Use of InstrRev would be quicker if using for VB programmers

Private Function zPathFileToFile(sFilePathName As String) As String
    Dim ThisChar As Long
    zPathFileToFile = sFilePathName 'Default return value
    For ThisChar = 0 To Len(sFilePathName) - 1
        If Mid$(sFilePathName, Len(sFilePathName) - ThisChar, 1) = "\" Then
            zPathFileToFile = Right$(sFilePathName, ThisChar)
            Exit For
        End If
    Next
End Function


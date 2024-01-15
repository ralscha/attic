package HTTP::Headers::UserAgent;

$VERSION = '1.00';

=head1 NAME

HTTP::Headers::UserAgent - Routine to detect web browser platform from User-Agent HTTP Header

=head1 SYNOPSIS

	use HTTP::Headers::UserAgent;
	$Platform = new HTTP::Headers::UserAgent::GetPlatform ($ENV{HTTP_USER_AGENT});

=cut

# Analyzing user agent like: HTTP_USER_AGENT=Mozilla/4.0 (compatible; MSIE 4.01; Windows NT)
sub GetPlatform {
	my ($UserAgent) = @_;
	my ($Platform);
	if ($UserAgent =~/Mozilla/o){
		$Platform = 'Win95' if $UserAgent=~/Windows 95|Win95|Win32|Windows CE/o;
		$Platform = 'Win98' if $UserAgent=~/Windows 98|Win98/o;
		$Platform = 'WinNT' if $UserAgent=~/Windows NT|WinNT/o;
		$Platform = 'UNIX'  if $UserAgent=~/X11/o;
		$Platform = 'MAC'   if $UserAgent=~/Macintosh|Mac_PPC|Mac_PowerPC|Mac_68|MC68/o;
		$Platform = 'Win3x' if $UserAgent=~/Win16|Windows 3.1|16bit/o;
		$Platform = 'OS2'   if $UserAgent=~/OS\/2/o;
		$Platform = 'Linux' if $UserAgent=~/linux/io;
	return $Platform;
	}
}

__END__

=head1 DESCRIPTION

The route will parse parameter string, which must be in HTTP User-Agent header form, and return 
the browser platform. The returned value is string with one of the following values:

	Win95, Win98, WinNT, UNIX, MAC, Win3x, OS2, Linux

If the platform is not one of these (i.e. one of the robots), the returned string is empty.

=head1 NOTES

Part of "WWW Cyrillic Encoding Suite" Get docs and newest version from http://www.neystadt.org/cyrillic/

Copyright (c) 1997-98, John Neystadt <http://www.neystadt.org/john/>. You may install this script on your 
web site for free. To obtain permision for or any other usage contact john@neystadt.org.

Drop me a line if you deploy this script on your site.

=head1 AUTHOR

Leonid Neishtadt <leonid@neystadt.org>, John Neystadt <john@neystadt.org>

=head1 SEE ALSO

perl(1).

=cut

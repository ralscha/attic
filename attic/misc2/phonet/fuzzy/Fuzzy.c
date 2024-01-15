/*    Unscharfe Suche im Information Retrieval               */
/*    (C) 1997 Reinhard Rapp                                 */
/*    ANSI C, getestet mit GNU C unter UNIX und MS-DOS,      */
/*    Borland C 5.0 unter Windows 95,                        */
/*    Metrowerks C (CW10) unter MacOS                        */

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

#define  MaxParLen 10000   /* maximale Laenge eines Absatzes */

/* Grossbuchstaben in Kleinbuchstaben, Satzzeichen und div.  */
/* Sonderzeichen in Leerzeichen umwandeln, Laenge ermitteln  */
long PrepareTheString(char* ConvStr, char* OriginStr)
{
  char*  TmpPtr;

  for (TmpPtr = ConvStr; *OriginStr; TmpPtr++, OriginStr++)
    {
      *TmpPtr = tolower(*OriginStr);
      if (*OriginStr < '0')  /* Satzzeichen neutralisieren   */
        *TmpPtr = ' ';
      else
        switch((unsigned char)*TmpPtr)
          {
            case 196: *TmpPtr = 228; break; /* ANSI-Umlaute  */
            case 214: *TmpPtr = 246; break;
            case 220: *TmpPtr = 252; break;
            case 142: *TmpPtr = 132; break; /* ASCII-Umlaute */
            case 153: *TmpPtr = 148; break;
            case 154: *TmpPtr = 129; break;
            case ':': *TmpPtr = ' '; break;
            case ';': *TmpPtr = ' '; break;
            case '<': *TmpPtr = ' '; break;
            case '>': *TmpPtr = ' '; break;
            case '=': *TmpPtr = ' '; break;
            case '?': *TmpPtr = ' '; break;
            case '[': *TmpPtr = ' '; break;
            case ']': *TmpPtr = ' '; break;
          }
    }
  *TmpPtr = '\0';
  return (TmpPtr - ConvStr);
}

/* sucht n-Gramme im Text und zaehlt die Treffer */
int NGramMatch(char* TextPara, char* SearchStr,
               int SearchStrLen, int NGramLen, int* MaxMatch)
{
  char    NGram[8];
  int     NGramCount;
  int     i, Count;

  NGram[NGramLen] = '\0';
  NGramCount = SearchStrLen - NGramLen + 1;

/* Suchstring in n-Gramme zerlegen und diese im Text suchen */
  for(i = 0, Count = 0, *MaxMatch = 0; i < NGramCount; i++)
    {
      memcpy(NGram, &SearchStr[i], NGramLen);

      /* bei Wortzwischenraum weiterruecken */
      if (NGram[NGramLen - 2] == ' ' && NGram[0] != ' ')
          i += NGramLen - 3;
      else
        {
          *MaxMatch  += NGramLen;
          if(strstr(TextPara, NGram)) Count++;
        }
    }
  return Count * NGramLen;  /* gewichten nach n-Gramm-Laenge */
}

/* fuehrt die unscharfe Suche durch */
int FuzzyMatching(FILE *InFile, char* SearchStr, int Threshold)
{
  char    TextPara[MaxParLen] = " ";     /* vorn Leerzeichen */
  char    TextBuffer[MaxParLen];
  int     TextLen, SearchStrLen;
  int     NGram1Len, NGram2Len;
  int     MatchCount1, MatchCount2;
  int     MaxMatch1, MaxMatch2;
  float   Similarity, BestSim = 0.0;

  /* n-Gramm-Laenge in Abhaengigkeit vom Suchstring festlegen*/
  SearchStrLen = PrepareTheString(SearchStr, SearchStr);
  NGram1Len = 3;
  NGram2Len = (SearchStrLen < 7) ? 2 : 5;

  /* Text absatzweise lesen und durchsuchen */
  fseek(InFile, 0L, SEEK_SET);
  while (fgets(TextBuffer, MaxParLen - 1, InFile))
    {
      TextLen = PrepareTheString(&TextPara[1], TextBuffer);
      if (TextLen < MaxParLen - 2)
        {
          MatchCount1 = NGramMatch(TextPara, SearchStr,
                          SearchStrLen, NGram1Len, &MaxMatch1);
          MatchCount2 = NGramMatch(TextPara, SearchStr,
                          SearchStrLen, NGram2Len, &MaxMatch2);



          /* Trefferguete berechnen und Bestwert festhalten  */
          Similarity = 100.0
                     * (float)(MatchCount1 + MatchCount2)
                     / (float)(MaxMatch1 + MaxMatch2);

          if (Similarity > BestSim)
              BestSim = Similarity;

          /* Wenn Guete >= Schwellwert: Treffer ausgeben     */
          if(Similarity >= Threshold) {
			printf("%d\n", MatchCount1);
			printf("%d\n", MaxMatch1);
			printf("%d\n", MatchCount2);
			printf("%d\n", MaxMatch2);

              printf("\n[%.1f] %s\n", Similarity, TextBuffer);
		   }
        }
      else
        {
          printf("Absatz zu lang!\n");
          return 1;
        }
    }
  if (BestSim < Threshold)
    printf("Kein Treffer; Best Match war %.1f\n", BestSim);

  return 0;
}

main()
{
  FILE    *InFile;
  char    Filename[256];
  char    SearchStr[256] = " ";    /* fuehrendes Leerzeichen */
  char    TempStr[256];
  int     Threshold;
  int     Done = 0;

  printf("\n+------------------------------------------+\n");
  printf("| Unscharfe Suche im Information Retrieval |\n");
  printf("|         (C) 1997 Reinhard Rapp           |\n");
  printf("+------------------------------------------+\n\n");

  printf("<ENTER> ohne Parameter beendet das Programm\n\n\n");
  printf("Name der zu durchsuchenden Datei: ");
  gets(Filename);
  if (strcmp(Filename, ""))
    {
      if((InFile = fopen(Filename,"r")) == NULL)
        {
          printf("Datei %s kann nicht geoeffnet werden.\n",
                                              Filename);
          return(1);
        }

      while(!Done)
        {
          printf("\nSuchstring: ");
          gets(&SearchStr[1]);

          if(strcmp(SearchStr, " "))
            {
              strcat(SearchStr, " ");
              printf("\nMindesttrefferguete in Prozent: ");
              for (Threshold = 0; Threshold == 0;  )
                {
                  gets(TempStr);
                  if (strcmp(TempStr, ""))
                    {
                      Threshold = atoi(TempStr);
                      if (Threshold == 0)
                          printf("Zahl zwischen 1 und 100!\n");
                    }
                  else
                      Done = 1;
                }
              if (!Done) {
				  printf("\"%s\"", SearchStr);
                  Done = FuzzyMatching(InFile, SearchStr,
                                                    Threshold);
				}
            }
          else
              Done = 1;
        }
    }
  printf("\nTschuess!\n");
  fclose(InFile);
  return 0;
}


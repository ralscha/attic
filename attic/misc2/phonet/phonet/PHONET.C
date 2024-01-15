/****  Programm "phonet.c":              ****/
/****  Phonetische Stringumwandlung für  ****/
/****  die deutsche Sprache und Namen.   ****/
/****  Autor: Jörg Michael, Hannover     ****/
/****         c't 25/1999                ****/

#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <malloc.h>

/****  Falls Zeichenwert von Umlauten < 0  (z.B. DOS):  ****/
/****     #define  CHAR   unsigned char      ****/
/****  sonst (z.B. "iso8859-1"):   #define  CHAR  char  ****/
#define  CHAR  unsigned char

#define  ANZ_HASH     256
#define  TEST_CHAR    '\004'

/****  Makros für "phonet_init":  ****/
#define  IST_INITIALISIERT  1
#define  DO_TRACE           2
#define  CHECK_RULES        4

static int  phonet_init = 0;

/****  Liste der Umlaute, nebst Großschreibung  ****/
static CHAR umlaute[] = "ÄÖÜäöüß";
static CHAR umlaute_gr[] = "ÄÖÜÄÖÜß";

static CHAR gross[256];
static int  isletter[256];
static int  phonet_hash [2*ANZ_HASH];

/****  Ende-Marke für Stringtabelle  ****/
static CHAR ENDE[] = "";


/****  Liste der phonetischen Regeln.           ****/
/****  Aufbau:                                  ****/
/****    <suchstring>  <1.regel>  <2.regel>     ****/
/****  Syntax für Suchstrings:                  ****/
/****    <wort> [<->..] [<] [<0-9>] [^[^]] [$]  ****/
/****  (Am Ende von "wort" darf optional als    ****/
/****    regulärer Ausdruck EIN Array von       ****/
/****    Buchstaben stehen, das in '(' und')'   ****/
/****    eingeschlossen sein muss.)             ****/

/****  Alle Regeln werden per Initialisierung   ****/
/****    in Großschreibung umgewandelt.         ****/
/****  Die Reihenfolge der Regeln ist wichtig.  ****/
/****    So müssen z.B. die Umwandlungsregeln   ****/
/****    für "S" NACH den Regeln für "SCH"      ****/
/****    stehen (sonst Umwandlungsfehler).      ****/

static CHAR *phonet_rules[] =
  {
    "'",               "",      "",
    "´",               "",      "",
    "`$",              "",      "",
    "\"",              " ",     " ",
    ",",               " ",     " ",
    "-",               " ",     " ",
    " ",               " ",     " ",
    ".",               ".",     ".",
    ":",               ".",     ".",
    "äer-",            "e",     "e",
    "äu<",             "eu",    "eu",
    "äv(aeou)-<",      "ew",    NULL,
    "ä$",              "ä",     NULL,
    "ä<",              NULL,    "e",
    "ä",               "e",     NULL,
    "öer-",            "ö",     "ö",
    "över--<",         "öw",    NULL,
    "öv(aou)-",        "öw",    NULL,
    "über^^",          "üba",   "iba",
    "üer-",            "ü",     "i",
    "üver--<",         "üw",    NULL,
    "üv(aou)-",        "üw",    NULL,
    "ü",               NULL,    "i",
    "ß",               "s",     "z",
    "abelle$",         "abl",   "abl",
    "abell$",          "abl",   "abl",
    "abienne$",        "abin",  "abin",
    "acey$",           "azi",   "azi",
    "adv",             "atw",   NULL,
    "aeu<",            "eu",    "eu",
    "ae2",             "e",     "e",
    "agni-^",          "akn",   "akn",
    "agnie-",          "ani",   "ani",
    "agn(aeou)-$",     "ani",   "ani",
    "ah(aioöuüy)-",    "ah",    NULL,
    "aia2",            "aia",   "aia",
    "aie$",            "e",     "e",
    "aill(eou)-",      "ali",   "ali",
    "aine$",           "en",    "en",
    "aire$",           "er",    "er",
    "air-",            "e",     "e",
    "aise$",           "es",    "ez",
    "aissance$",       "esans", "ezanz",
    "aisse$",          "es",    "ez",
    "aix$",            "ex",    "ex",
    "aj(aäeioöuü)--",  "a",     "a",
    "aktie",           "axie",  "axie",
    "aktuel",         "aktuel", NULL,
    "aloi^",           "aloi",  "alui",  /**   Regeln NICHT   **/
    "aloy^",           "aloi",  "alui",  /**  zusammenfassen  **/
                                         /** wg. "check_rules" **/
    "amateu(rs)-",     "amatö", "anatö",
    "aner(bko)---^^",   "an",   NULL,
    "anhy(bdgmnp)---^^", "an",  NULL,
    "anielle$",        "aniel", "anil",
    "aniel",           "aniel", NULL,
    "anti^^",          "anti",  "anti",
    "anver^^",         "anfa",  "anfa",
    "atia$",           "atia",  "atia",
    "atia(ns)--",      "ati",   "ati",
    "ati(aäoöuü)-",    "azi",   "azi",
    "auau--",          "",      "",
    "auere$",          "auere", NULL,
    "auere(ns)-$",     "auere", NULL,
    "auere(aiouy)--",  "auer",  NULL,
    "auer(aäioöuüy)-", "auer",  NULL,
    "auer<",           "aua",   "aua",
    "auf^^",           "auf",   "auf",
    "ault$",           "o",     "u",
    "ausse$",          "os",    "uz",
    "aus(st)-^",       "aus",   "aus",
    "aus^^",           "aus",   "aus",
    "auto^^",          "auto",  "auto",
    "aux(iy)-",        "aux",   "aux",
    "aux",             "o",     "u",
    "au",              "au",    "au",
    "aver--<",         "aw",    NULL,
    "avier$",          "awie",  "afie",
    "av(ei)-^",        "aw",    NULL,
    "av(aou)-",        "aw",    NULL,
    "ayer--<",         "ei",    "ei",
    "ay(aäeioöuü)--",  "a",     "a",
    "a(ijy)<",         "ei",    "ei",
    "beau^$",          "bo",    NULL,
    "bea(bcmnru)-^",   "bea",   "bea",
    "beat(aeimoru)-^", "beat",  "beat",
    "beige^$",         "besh",  "bez",
    "ber(dt)-",        "ber",   NULL,
    "bern(dt)-",       "bern",  NULL,
    "be(lmnrst)-^",    "be",    "be",
    "bette$",          "bet",   "bet",
    "bic$",            "biz",   "biz",
    "bowl(ei)-",       "bol",   "bul",
    "bp(aäeioöruüy)-", "b",     "b",
    "b(sßz)$",         "bs",    NULL,
    "budget7",         "büge",  "bike",
    "buffet7",         "büfe",  "bife",
    "bylle$",          "bile",  "bile",
    "byll$",           "bil",   "bil",
    "byte<",           "beit",  "beit",
    "by9^",            "bü",    NULL,
    "c.^",             "c.",    "c.",
    "cä-",             "z",     "z",
    "cü$",             "zü",    "zi",
    "cach(ei)-^",      "kesh",  "kez",
    "cae--",           "z",     "z",
    "ca(iy)$",         "zei",   "zei",
    "cch",             "z",     "z",
    "cce-",            "x",     "x",
    "ce(eijuy)--",     "z",     "z",
    "cent<",           "zent",  "zent",
    "cerst(ei)----^",  "ke",    "ke",
    "cer$",            "za",    "za",
    "ce3",             "ze",    "ze",
    "chao(st)-",       "kao",   "kau",
    "champio-^",      "shempi", "zenbi",
    "char(ai)-^",      "kar",   "kar",
    "chau(cdfsvwxz)-", "sho",   "zu",
    "che(cf)-",        "she",   "ze",
    "chem-^",          "ke",    "ke",  /**  bzw. "che", "ke" **/
    "cheque<",         "shek",  "zek",
    "chi(cfgpvw)-",    "shi",   "zi",
    "ch(aeuy)-<^",     "sh",    "z",
    "chk-",            "",      "",
    "chris-",          "kri",   NULL,
    "chro-",           "kr",    NULL,
    "ch(lor)-<^",      "k",     "k",
    "chst-",           "x",     "x",
    "ch(sßxz)3",       "x",     "x",
    "ch^",             "k",     "k",   /**  bzw. "ch", "k"  **/
    "ch",              "ch",    "k",
    "cier$",           "zie",   "zie",
    "cyb-^",           "zei",   "zei",
    "cy9^",            "zü",    "zi",
    "c(ijy)-3",        "z",     "z",
    "ckst",            "xt",    "xt",
    "ck(sßxz)3",       "x",     "x",
    "c(ck)-",          "",      "",
    "claudet---",      "klo",   "klu",
    "claudine^$",     "klodin", "klutin",
    "cole$",           "kol",   "kul",
    "couch",           "kaush", "kauz",
    "cques$",          "k",     "k",
    "cque",            "k",     "k",
    "creat-^",         "krea",  "krea",
    "cst",             "xt",    "xt",
    "cs<^",            "z",     "z",
    "c(sßx)",          "x",     "x",
    "ct(sßxz)",        "x",     "x",
    "cz<",             "z",     "z",
    "c<",              "k",     "k",
    "d'h^",            "d",     "t",
    "d´h^",            "d",     "t",
    "d`h^",            "d",     "t",
    "d's3$",           "z",     "z",
    "d´s3$",           "z",     "z",
    "daher^$",         "daher", NULL,
    "davo(nr)-^$",     "dafo",  "tafu",
    "dd(sz)--<",       "",      "",
    "dd9",             "d",     NULL,
    "depot7",          "depo",  "tebu",
    "design",         "disein", "tizein",
    "de(lmnrst)-3^",   "de",    "te",
    "dette$",          "det",   "tet",
    "dh$",             "t",     NULL,
    "dic$",            "diz",   "tiz",
    "didr-^",          "dit",   NULL,
    "diedr-^",         "dit",   NULL,
    "dj(aeiou)-^",     "i",     "i",
    "dry9^",           "drü",   NULL,
    "ds(ch)--<",       "t",     "t",
    "dst",             "zt",    "zt",
    "dt-",             "",      "",
    "duis-^",          "dü",    "ti",
    "durch^^",         "durch", "turk",
    "dva$",            "twa",   NULL,
    "dy9^",            "dü",    NULL,
    "d(aäeioöruüy)-",  "d",     NULL,
    "dzs(ch)--",       "t",     "t",
    "d(sßz)",          "z",     "z",
    "d^",              "d",     NULL,
    "d",               "t",     "t",
    "eault$",          "o",     "u",
    "eaux$",           "o",     "u",
    "eau",             "o",     "u",
    "eav",             "iw",    "if",
    "eas3$",           "eas",   NULL,
    "ea(aäeioöüy)-3",  "ea",    "ea",
    "ea3$",            "ea",    "ea",
    "ea3",             "i",     "i",
    "eben^^",          "ebn",   "ebn",
    "ee9",             "e",     "e",
    "ehe(iuy)--1",     "eh",    NULL,
    "eh(aäioöuüy)-1",  "eh",    NULL,
    "eiei--",          "",      "",
    "eiere^$",         "eiere", NULL,
    "eiere$",          "eiere", NULL,
    "eiere(ns)-$",     "eiere", NULL,
    "eiere(aiouy)--",  "eier",  NULL,
    "eier(aäioöuüy)-", "eier",  NULL,
    "eier<",           "eia",   NULL,
    "eih--",           "e",     "e",
    "eille$",          "ei",    "ei",
    "ei",              "ei",    "ei",
    "ej$",             "ei",    "ei",
    "eliz^",           "elis",  NULL,
    "elz^",            "els",   NULL,
    "el-^",            "e",     "e",
    "el(dkl)--1",      "e",     "e",
    "el(mnt)--1$",     "e",     "e",
    "elyne$",          "eline", "eline",
    "elyn$",           "elin",  "elin",
    "el(aäeioöuüy)-1", "el",    "el",
    "el-1",            "l",     "l",
    "em-^",            NULL,    "e",
    "em(dfkmpqt)--1",  NULL,    "e",
    "em(aäeioöuüy)--1", NULL,   "e",
    "em-1",            NULL,    "n",
    "en-^",            "e",     "e",
    "entuel",         "entuel", NULL,
    "en(cdgkqt)--1",   "e",     "e",
    "enz(aeiouy)--1",  "en",    "en",
    "en(aäeinoöuüy)-1","en",    "en",
    "en-<1",           "n",     "n",
    "erh(aäeioöuü)-^", "erh",   "er",
    "er-^",            "e",     "e",
    "ert1$",           "at",    NULL,
    "er(dglkmnrqtzß)-1", "er",  NULL,
    "er(aäeioöuüy)-1", "er",    "a",
    "er1$",            "a",     "a",
    "er<1",            "a",     "a",
    "eti(aäoöüu)-",    "ezi",   "ezi",
    "euere$",          "euere", NULL,
    "euere(ns)-$",     "euere", NULL,
    "euere(aiouy)--",  "euer",  NULL,
    "euer(aäioöuüy)-", "euer",  NULL,
    "euer<",           "eua",   NULL,
    "eueu--",          "",      "",
    "euille$",         "ö",     "ö",
    "eur$",            "ör",    "ör",
    "eux",             "ö",     "ö",
    "eusz$",           "eus",   NULL,
    "eutz$",           "eus",   NULL,
    "euys$",           "eus",   "euz",
    "euz$",            "eus",   NULL,
    "eu",              "eu",    "eu",
    "ever--<1",        "ew",    NULL,
    "ev(äoöuü)-1",     "ew",    NULL,
    "eyer<",           "eia",   "eia",
    "ey<",             "ei",    "ei",
    "fans--^$",        "fe",    "fe",
    "fan-^$",          "fe",    "fe",
    "fault-",          "fol",   "ful",
    "fee(dl)-",        "fi",    "fi",
    "fehler",          "fela",  "fela",
    "fe(lmnrst)-3^",   "fe",    "fe",
    "fond7",           "fon",   "fun",
    "frain$",          "fra",   "fra",
    "friseu(rs)-",     "frisö", "frizö",
    "fy9^",            "fü",    NULL,
    "g's$",            "x",     "x",
    "g´s$",            "x",     "x",
    "gags^$",          "gex",   "kex",
    "gag^$",           "gek",   "kek",
    "gd",              "kt",    "kt",
    "gegen^^",         "gegn",  "kekn",
    "ge(lmnrst)-3^",   "ge",    "ke",
    "ger(dkt)-",       "ger",   NULL,
    "gette$",          "get",   "ket",
    "g(ck)-",          "",      "",
    "ggf.",            "gf.",   NULL,
    "gg-",             "",      "",
    "gh",              "g",     NULL,
    "gi(ao)-^",        "i",     "i",
    "gion$",           "kion",  "kiun",
    "gius-^",          "iu",    "iu",
    "gmbh^$",          "gmbh",  "gmbh",
    "gnac$",           "niak",  "niak",
    "gnon$",           "nion",  "niun",
    "gn$",             "n",     "n",
    "goncal-^",        "gonza", "kunza",
    "gry9^",           "grü",   NULL,
    "gs(ch)--",        "k",     "k",
    "gst",             "xt",    "xt",
    "g(sßxz)",         "x",     "x",
    "guck-",           "ku",    "ku",
    "gui-^",           "g",     "k",
    "gy9^",            "gü",    NULL,
    "g(aäeiloöruüy)-", "g",     NULL,
    "g^",              "g",     NULL,
    "g",               "k",     "k",
    "h.^",             NULL,    "h.",
    "häu--1",          "h",     NULL,
    "ha(hiuy)--1",     "h",     NULL,
    "hannove-^",       "hanof", NULL,
    "haven7$",         "hafn",  NULL,
    "head-",           "he",    "e",
    "he(lmnrst)-3^",   "he",    "e",
    "he(lmn)-1",       "e",     "e",
    "heur1$",          "ör",    "ör",
    "he(hiuy)--1",     "h",     NULL,
    "hih(aäeioöuüy)-1", "ih",   NULL,
    "hlh(aäeioöuüy)-1", "lh",   NULL,
    "hmh(aäeioöuüy)-1", "mh",   NULL,
    "hnh(aäeioöuüy)-1", "nh",   NULL,
    "ho(hiy)--1",       "h",    NULL,
    "hrh(aäeioöuüy)-1", "rh",   NULL,
    "huh(aäeioöuüy)-1", "uh",   NULL,
    "hui--1",          "h",     NULL,
    "hygien^",        "hükien", NULL,
    "hy9^",            "hü",    NULL,
    "h^",              "h",     "",
    "h",               "",      "",
    "iec$",            "iz",    "iz",
    "iei-3",           "",      "",
    "iell3",           "iel",   "iel",
    "ienne$",          "in",    "in",
    "ierre$",          "ier",   "ier",
    "iette$",          "it",    "it",
    "ieu",             "iö",    "iö",
    "ie<4",            "i",     "i",
    "ight3$",          "eit",   "eit",
    "igni(eo)-",       "ini",   "ini",
    "ign(aeou)-$",     "ini",   "ini",
    "iher(dglkrt)--1", "ihe",   NULL,
    "ihe(iuy)--",      "ih",    NULL,
    "ih(aioöuüy)-",    "ih",    NULL,
    "ij(aou)-",        "i",     "i",
    "ij$",             "i",     "i",
    "ij<",             "ei",    "ei",
    "ikole$",          "ikol",  "ikul",
    "illan(stz)--",    "ilia",  "ilia",
    "illar(dt)--",     "ilia",  "ilia",
    "inver-",          "inwe",  "infe",
    "iti(aäoöuü)-",    "izi",   "izi",
    "iusz$",           "ius",   NULL,
    "iutz$",           "ius",   NULL,
    "iuz$",            "ius",   NULL,
    "iver--<",         "iw",    NULL,
    "ivier$",          "iwie",  "ifie",
    "iv(äoöuü)-",      "iw",    NULL,
    "iv<3",            "iw",    NULL,
    "iy2",             "i",     NULL,
    "javie---<^",      "xa",    "za",
    "jean^$",          "ia",    "ia",
    "jean-^",          "ia",    "ia",
    "jer-^",           "ie",    "ie",
    "je(lmnst)-",      "ie",    "ie",
    "ji^",             "ji",    NULL,
    "jor(gk)^$",       "iörk",  "iörk",
    "j",               "i",     "i",
    "kc(äeij)-",       "x",     "x",
    "kd",              "kt",    NULL,
    "ke(lmnrst)-3^",   "ke",    "ke",
    "kg(aäeiloöruüy)-", "k",    NULL,
    "kh<^",            "k",     "k",
    "kic$",            "kiz",   "kiz",
    "kle(lmnrst)-3^",  "kle",   "kle",
    "kotele-^",        "kotl",  "kutl",
    "kreat-^",         "krea",  "krea",
    "krüs(tz)--^",     "kri",   NULL,
    "krys(tz)--^",     "kri",   NULL,
    "kry9^",           "krü",   NULL,
    "kst",             "xt",    "xt",
    "k(sßxz)",         "x",     "x",
    "kti(aiou)-3",     "xi",    "xi",
    "kt(sßxz)",        "x",     "x",
    "ky9^",            "kü",    NULL,
    "larve-",          "larf",  "larf",
    "leand-^",         "lean",  "lean",
    "lel-",            "le",    "le",
    "le(mnrst)-3^",    "le",    "le",
    "lette$",          "let",   "let",
    "lfgnag-",         "lfgan", "lfkan",
    "lic$",            "liz",   "liz",
    "live^$",          "leif",  "leif",
    "lui(gs)--",       "lu",    "lu",
    "lv(aio)-",        "lw",    NULL,
    "ly9^",            "lü",    NULL,
    "manuel",         "manuel", NULL,
    "masseu(rs)-",     "masö",  "nazö",
    "maurice",         "moris", "nuriz",
    "mbh^$",           "mbh",   "mbh",
    "mb(ßz)$",         "ms",    NULL,
    "mb(sßz)-",        "m",     "n",
    "mc9^",            "mk",    "nk",
    "memoir-^",        "memoa", "nenua",
    "merhaven$",      "mahafn", NULL,
    "me(lmnrst)-3^",   "me",    "ne",
    "men(stz)--3",     "me",    NULL,
    "men$",            "men",   NULL,
    "miguel",          "migl",  "nikl",
    "mike^$",          "meik",  "neik",
    "mn$",             "m",     NULL,
    "mn",              "n",     "n",
    "mpjute-",         "mput",  "nbut",
    "mp(ßz)$",         "ms",    NULL,
    "mp(sßz)-",        "m",     "n",
    "mp(bdjlmnpqrtvw)-", "mb",  "nb",
    "my9^",            "mü",    NULL,
    "m(ßz)$",          "ms",    NULL,
    "m",               NULL,    "n",
    "nach^^",          "nach",  "nak",
    "nadine",          "nadin", "natin",
    "naiv--",          "na",    "na",
    "naise$",          "nese",  "neze",
    "ncoise$",         "soa",   "zua",
    "ncois$",          "soa",   "zua",
    "ndro(cdktz)-",    "ntro",  NULL,
    "nd(bfgjlmnpqvw)-", "nt",   NULL,
    "neben^^",         "nebn",  "nebn",
    "ne(lmnrst)-3^",   "ne",    "ne",
    "nen-3",           "ne",    "ne",
    "nette$",          "net",   "net",
    "ng(bdfjlmnpqrtvw)-", "nk", "nk",
    "nichts^^",        "nix",   "nix",
    "nicht^^",         "nicht", "nikt",
    "nine$",           "nin",   "nin",
    "non^^",           "non",   "nun",
    "not^^",           "not",   "nut",
    "nsz-",            "ns",    NULL,
    "nti(aiou)-3",     "nzi",   "nzi",
    "ntiel--3",        "nzi",   "nzi",
    "nylon",          "neilon", "neilun",
    "ny9^",            "nü",    NULL,
    "nz(bdfgklmnpqrstvwx)-", "ns", NULL,
    "nd(sßz)$",        "ns",    "nz",
    "nt(sßz)$",        "ns",    "nz",
    "nd's$",           "ns",    "nz",
    "nt's$",           "ns",    "nz",
    "nd´s$",           "ns",    "nz",
    "nt´s$",           "ns",    "nz",
    "nsts$",           "ns",    "nz",
    "n(sßz)$",         "ns",    NULL,
    "obere-",          "ober",  NULL,
    "ober^^",          "oba",   "uba",
    "oe2",             "ö",     "ö",
    "ognie-",          "oni",   "uni",
    "ogn(aeou)-$",     "oni",   "uni",
    "oh(aioöuüy)-",    "oh",    NULL,
    "oie$",            "ö",     "ö",
    "oir$",            "oar",   "uar",
    "oix",             "oa",    "ua",
    "oi<3",            "eu",    "eu",
    "oj(aäeioöuü)--",  "o",     "u",
    "okay^$",          "oke",   "uke",
    "olyn$",           "olin",  "ulin",
    "oo(dl)-",         "u",     NULL,
    "oti(aäoöuü)-",    "ozi",   "uzi",
    "oui^",            "wi",    "fi",
    "ouille$",         "ulie",  "ulie",
    "ou(dt)-^",        "au",    "au",
    "ouse$",           "aus",   "auz",
    "out-",            "au",    "au",
    "ou",              "u",     "u",
    "over--<",         "ow",    NULL,
    "ov(aou)-",        "ow",    NULL,
    "ows$",            "os",    "uz",
    "oyer",            "oia",   NULL,
    "oy(aäeioöuü)--",  "o",     "u",
    "o(jy)<",          "eu",    "eu",
    "oz$",             "os",    NULL,
    "o",               NULL,    "u",
    "p.^",             NULL,    "p.",
    "patien--^",       "pazi",  "pazi",
    "pensio-^",        "pansi", "panzi",
    "pe(lmnrst)-3^",   "pe",    "pe",
    "pfer-^",          "fe",    "fe",
    "p(fh)<",          "f",     "f",
    "polyp-",          "polü",  NULL,
    "poly^^",          "poli",  "puli",
    "portrait7",      "portre", "purtre",
    "pp(fh)--<",       "b",     "b",
    "pp-",             "",      "",
    "prix^$",          "pri",   "pri",
    "ps-^^",           "p",     NULL,
    "p(sßz)^",         NULL,    "z",
    "p(sßz)$",         "bs",    NULL,
    "pti(aäoöuü)-3",   "bzi",   "bzi",
    "py9^",            "pü",    NULL,
    "pic^$",           "pik",   "pik",
    "pic$",            "piz",   "piz",
    "p(aäeioöruüy)-",  "p",     "p",
    "p^",              "p",     NULL,
    "p",               "b",     "b",
    "que(lmnrst)-3",   "kwe",   "kfe",
    "que$",            "k",     "k",
    "qui(ns)$",        "ki",    "ki",
    "quiz7",           "kwis",  NULL,
    "qu",              "kw",    "kf",
    "q<",              "k",     "k",
    "rch",             "rch",   "rk",
    "recherch^",     "reshash", "rezaz",
    "re(alst)-3^",     "re",    NULL,
    "rer$",            "ra",    "ra",
    "re(mnr)-4",       "re",    "re",
    "rette$",          "ret",   "ret",
    "reuz$",           "reuz",  NULL,
    "rh<^",            "r",     "r",
    "rja(mn)--",       "ri",    "ri",
    "rti(aäoöuü)-3",   "rzi",   "rzi",
    "rv(aeou)-3",      "rw",    NULL,
    "ry(kn)-$",        "ri",    "ri",
    "ry9^",            "rü",    NULL,
    "safe^$",          "seif",  "zeif",
    "sauce-^",         "sos",   "zuz",
    "schsch---7",      "",      "",
    "schtsch",         "sh",    "z",
    "sc(hz)<",         "sh",    "z",
    "sc",              "sk",    "zk",
    "selbstst--7^^",   "selb",  "zelb",
    "selbst7^^",      "selbst", "zelbzt",
    "service7^",      "sörwis", "zörfiz",
    "servi-^",         "serw",  NULL,
    "se(lmnrst)-3^",   "se",    "ze",
    "sette$",          "set",   "zet",
    "shp-^",           "s",     "z",
    "shst",            "sht",   "zt",
    "shtsh",           "sh",    "z",
    "sht",             "st",    "z",
    "shy9^",           "shü",   NULL,
    "sh^^",            "sh",    NULL,
    "sh3",             "sh",    "z",
    "siegli-^",        "sikl",  "zikl",
    "sigli-^",         "sikl",  "zikl",
    "sight",           "seit",  "zeit",
    "sign",            "sein",  "zein",
    "ski(npz)-",       "ski",   "zki",
    "ski<^",           "shi",   "zi",
    "sound-",          "saun",  "zaun",
    "staats^^",        "staz",  "ztaz",
    "stadt^^",         "stat",  "ztat",
    "start^^",         "start", "ztart",
    "staurant7",      "storan", "zturan",
    "steak-",          "ste",   "zte",
    "stern",           "stern", NULL,
    "straf^^",         "straf", "ztraf",
    "st's$",           "z",     "z",
    "st´s$",           "z",     "z",
    "stst--",          "",      "",
    "sts(acehiouäüö)--", "st",  "zt",
    "st(sz)",          "z",     "z",
    "s(ptw)-^^",       "s",     NULL,
    "sp",              "sp",    NULL,
    "styn(ae)-$",      "stin",  "ztin",
    "st",              "st",    "zt",
    "sv(aeiou)-<^",    "sw",    NULL,
    "sül(kvw)--^",     "si",    NULL,
    "syb(iy)--^",      "sib",   NULL,
    "syl(kvw)--^",     "si",    NULL,
    "sy9^",            "sü",    NULL,
    "sze(npt)-^",      "ze",    "ze",
    "szi(eln)-^",      "zi",    "zi",
    "szcz<",           "sh",    "z",
    "szt<",            "st",    "zt",
    "sz<3",            "sh",    "z",
    "s",               NULL,    "z",
    "t's3$",           "z",     "z",
    "t´s3$",           "z",     "z",
    "tch",             "sh",    "z",
    "td(aäeioöruüy)-", "t",     NULL,
    "teat-^",          "tea",   "tea",
    "te(lmnrst)-3^",   "te",    "te",
    "th<",             "t",     "t",
    "tic$",            "tiz",   "tiz",
    "toas-^",          "to",    "tu",
    "toilet-",         "tole",  "tule",
    "toin-",           "toa",   "tua",
    "traini-",         "tren",  "tren",
    "tsch",            "sh",    "z",
    "tsh",             "sh",    "z",
    "tst",             "zt",    "zt",
    "t(sß)",           "z",     "z",
    "tt(sz)--<",       "",      "",
    "tt9",             "t",     "t",
    "tv^$",            "tv",    "tv",
    "ty9^",            "tü",    NULL,
    "tz-",             "",      "",
    "ueber^^",         "üba",   "iba",
    "ue2",             "ü",     "i",
    "uh(aoöuüy)-",     "uh",    NULL,
    "uie$",            "ü",     "i",
    "um^^",            "um",    "un",
    "untere--",        "unte",  "unte",
    "unter^^",         "unta",  "unta",
    "unver^^",         "unfa",  "unfa",
    "un^^",            "un",    "un",
    "uti(aäoöuü)-",    "uzi",   "uzi",
    "uve-4",           "uw",    NULL,
    "uy2",             "ui",    NULL,
    "v.^",             "v.",    NULL,
    "vacl-^",          "waz",   "faz",
    "vac$",            "waz",   "faz",
    "vanes-^",         "wane",  NULL,
    "vatro-",          "watr",  NULL,
    "va(dhjnt)--^",    "f",     NULL,
    "vedd-^",          "fe",    "fe",
    "ve(behiu)--^",    "f",     NULL,
    "vel(bdlmnt)-^",   "fel",   NULL,
    "ventz-^",         "fen",   NULL,
    "ven(nrsz)-^",     "fen",   NULL,
    "ver(ab)-^$",      "wer",   NULL,
    "verbal^$",       "werbal", NULL,
    "verbal(eins)-^", "werbal", NULL,
    "vertebr--",       "werte", NULL,
    "verein",         "ferein", "faein",
    "veren(aeiou)-^",  "weren", NULL,
    "verifi",          "werifi",NULL,
    "veron(aeiou)-^",  "weron", NULL,
    "versen^",         "fersn", "fazn",
    "versiert--^",     "wersi", NULL,
    "versio--^",       "wers",  NULL,
    "versus",          "wersus",NULL,
    "verti(gk)-",      "werti", NULL,
    "ver^^",           "fer",   "fa",
    "ver$",            "wa",    NULL,
    "ver",             "fa",    "fa",
    "vet(ht)-^",       "fet",   "fet",
    "vette$",          "wet",   "fet",
    "ve^",             "we",    NULL,
    "vic$",            "wiz",   "fiz",
    "viel",            "fil",   "fil",
    "view",            "wiu",   "fiu",
    "vis(aceikuvwz)-<^", "wis", NULL,
    "vi(els)--^",      "f",     NULL,
    "vlie--^",         "fl",    NULL,
    "voka-^",          "wok",   NULL,
    "vol(atuvw)--^",   "wo",    NULL,
    "v(aeijlru)-<",    "w",     NULL,
    "vor^^",           "for",   "fur",
    "vv9",             "w",     NULL,
    "vy9^",            "wü",    "fi",
    "v(üy)-",          "w",     NULL,
    "v<",              "f",     "f",
    "we(lmnrst)-3^",   "we",    "fe",
    "wer(dst)-",       "wer",   NULL,
    "wic$",            "wiz",   "fiz",
    "wiederu--",       "wide",  NULL,
    "wieder^^",        "wida",  "fita",
    "wisuel",         "wisuel", NULL,
    "wy9^",            "wü",    "fi",
    "w(bdfgjklmnpqrt)-", "f",   NULL,
    "w$",              "f",     NULL,
    "w",               NULL,    "f",
    "xe(lmnrst)-3^",   "xe",    "xe",
    "xy9^",            "xü",    NULL,
    "x<^",             NULL,    "z",
    "xhaven$",         "xafn",  NULL,
    "x(csz)",          "x",     "x",
    "xts(ch)--",       "xt",    "xt",
    "xt(sz)",          "z",     "z",
    "y.^",             "y.",    NULL,
    "ye(lmnrst)-3^",   "ie",    "ie",
    "ye-3",            "i",     "i",
    "yor(gk)^$",       "iörk",  "iörk",
    "y(aou)-<7",       "i",     "i",
    "y(bklmnprstx)-1", "ü",     NULL,
    "yves^$",          "if",    "if",
    "yvonne^$",        "iwon",  "ifun",
    "y",               "i",     "i",
    "zc(aou)-",        "sk",    "zk",
    "ze(lmnrst)-3^",   "ze",    "ze",
    "zh<",             "z",     "z",
    "zs(cht)--",       "",      "",
    "zs",              "sh",    "z",
    "zuerst",         "zuerst", "zuerst",
    "zurück^^",        "zurük", "zurik",
    "zuver^^",         "zufa",  "zufa",
    "zyk3$",           "zik",   NULL,
    "zy9^",            "zü",    NULL,
    "z(vw)7^",         "sw",    NULL,
    ENDE,              ENDE,    ENDE
  };



static void trace_info (CHAR text[], int n, CHAR fehler[])
/****  Protokollinformationen ausgeben  ****/
{
  CHAR *s,*s2,*s3;
  s  = (phonet_rules[n] == NULL)  ?  "(NULL)" : phonet_rules[n];
  s2 = (phonet_rules[n+1] == NULL) ? "(NULL)" : phonet_rules[n+1];
  s3 = (phonet_rules[n+2] == NULL) ? "(NULL)" : phonet_rules[n+2];

  printf ("%s %d:  \"%s\"%s\"%s\" %s\n",
        text, ((n/3)+1), s, s2, s3, fehler);
}



static void initialize_phonet (void)
/****  Initialisierung  ****/
{
  int i,k;
  CHAR *s;
  phonet_init = phonet_init | IST_INITIALISIERT;

  /****  Arrays "gross" und "isletter" erzeugen  ****/
  for (i=0; i<256; i++)
    {
     gross[i] = (islower (i)) ?  (CHAR) toupper(i) : (CHAR) i;
     isletter[i] = (isalpha (i)) ?  1 : 0;
    }
  for (i=0; umlaute[i] != '\0'; i++)
    {
     gross [umlaute[i]] = umlaute_gr[i];
     isletter [umlaute[i]] = 1;
    }
  for (i=0; i< 2*ANZ_HASH; i++)
    {
     phonet_hash[i] = -1;
    }

  for (i=0; phonet_rules[i] != ENDE; i++)
    {
     s = phonet_rules[i];
     /****  phonetische Regel in Großbuchstaben umwandeln  ****/
     while (s != NULL  &&  *s != '\0')
       {
        *s = gross [*s];
        s++;
       }

     if (s != NULL  &&  i % 3 == 0)
       {
        /****  Hash-Wert setzen  ****/
        k = (int) phonet_rules[i][0];

        if (phonet_hash[k] < 0
        &&  phonet_rules[i+1] != NULL)
          {
           /****  erste Regeln  ****/
           phonet_hash[k] = i;
          }
        if (phonet_hash [k+ANZ_HASH] < 0
        &&  phonet_rules[i+2] != NULL)
          {
           /****  zweite Regeln  ****/
           phonet_hash [k+ANZ_HASH] = i;
          }
       }
    }
}



static void string_prepare (CHAR *text, CHAR *s, CHAR *s2)
/****  Hilfsfunktion für "check_rules":     ****/
/****  "strcpy (text,s)" plus Einbauen von  ****/
/****  'TEST_CHAR' und '-', falls nötig     ****/
{
  if (*s != '\0')
    {
     *text = *s;
     text++;
     s++;
    }
  while (*s != '\0'  &&  ! isdigit (*s)
  &&  strchr ("-<^$", *s) == NULL)
    {
     *text = *s;
     text++;
     s++;
    }
  if (strchr (s2,'-') != NULL  ||  strchr (s2,'$') == NULL)
    {
     *text = TEST_CHAR;
     text++;
     *text = '-';
     text++;
    }
  strcpy (text, s);
}




int phonet (CHAR wort[], CHAR ziel[], int anz, int modus)

/****  Phonetische Umwandlung durchführen.  ****/
/****    ("ziel" == "wort" ist erlaubt).    ****/
/****  "anz" = Länge von "ziel" inkl. '\0'. ****/
/****  modus <= 1:  Erste Regeln anwenden   ****/
/****               (dabei "verschwinden"   ****/
/****                Ä,ß,C,J,Q,V,Y)         ****/

/****  modus >= 2:  Zweite Regeln anwenden  ****/
/****               (gröbere Umwandlung;    ****/
/****                es "verschwinden"      ****/
/****                Ä,Ü,ß,C,D,G,H,J,O,P,   ****/
/****                Q,S,V,W,X,Y)           ****/
/****  Ergebnis:  >= 0:  Länge von "ziel"   ****/
/****            sonst:  Fehler             ****/
{
 int  i,j,k,n,p,z;
 int  k0,n0,p0,z0;
 CHAR c,c0,*s;
 CHAR *ww,hilf[51];

 if (! (phonet_init & IST_INITIALISIERT))
   {
    /****  Initialisierung  ****/
    initialize_phonet();
   }
 if (ziel == NULL  ||  wort == NULL  ||  anz <= 0)
   {
    /****  ungültige Argumente  ****/
    if (phonet_init & DO_TRACE)
      {
       printf ("Fehler: ungültige Argumente angegeben.\n");
      }
    return (-1);
   }

 modus = (modus <= 1) ?  1 : 2;
 ww = hilf;
 i = (int) strlen (wort);
 if (i > 50)
   {
    /****  String mit "Ueberlänge"  ****/
    ww = malloc ((size_t) (i+1));
    if (ww == NULL)
      {
       /****  "malloc" fehlgeschlagen  ****/
       if (phonet_init & DO_TRACE)
         {
          printf ("Fehler: \"malloc\" fehlgeschlagen.\n");
         }
       strcpy (ziel,"");
       return (-2);
      }
   }

 /****  String kopieren und in Großschreibung umwandeln  ****/
 i = 0;
 while (wort[i] != '\0')
   {
    ww[i] = gross [wort[i]];
    i++;
   }
 ww[i] = '\0';
 wort = ww;
 if (phonet_init & DO_TRACE)
   {
    printf ("\n\nPhonetische Umwandlung für  :  \"%s\"\n", ww);
    printf ("(nach %d. Regelsatz)\n", modus);
   }

 /****  Wort prüfen  ****/
 modus = (modus <= 1) ?  0 : ANZ_HASH;
 i = 0;
 j = 0;
 z = 0;
 while ((c = wort[i]) != '\0')
   {
    if (phonet_init & DO_TRACE)
      {
       printf ("\nPrüfe Position %d:  Wort = \"%s\",", j, wort+i);
       printf ("  Ziel = \"%.*s\"\n", j, ziel);
      }
    n = phonet_hash [(int) c + modus];
    z0 = 0;

    if (n >= 0)
      {
       /****  alle Regeln zum gleichen Buchstaben testen  ****/
       while (phonet_rules[n] ==NULL  ||  phonet_rules[n][0] == c)
         {
          if (phonet_rules [n] == NULL
          ||  phonet_rules [n+1+(modus/ANZ_HASH)] == NULL)
            {
             /****  Dies ist keine Umwandlungsregel  ****/
             n += 3;
             continue;
            }
          if (phonet_init & DO_TRACE)
            {
             trace_info ("> Regel Nr.", n, "wird geprüft");
            }

          /****  ganzen String prüfen  ****/
          k = 1;   /****  Anzahl gefundener Buchstaben  ****/
          p = 5;   /****  Default-Priorität  ****/
          s = phonet_rules[n];
          s++;     /****  wichtig fuer (s.u.)  "*(s-1)"  ****/

          while (*s != '\0'  &&  wort[i+k] == *s
          &&  ! isdigit (*s)  &&  strchr ("(-<^$", *s) == NULL)
            {
             k++;
             s++;
            }
          if (phonet_init & CHECK_RULES)
            {
             /****  Regelprüfung mittels "check_rules"  ****/
             while (*s != '\0'  &&  wort[i+k] == *s)
               {
                k++;
                s++;
               }
            }
          if (*s == '(')
            {
             /****  Buchstabenbereich prüfen  ****/
             if (isletter [wort[i+k]]
             &&  strchr (s+1, wort[i+k]) != NULL)
               {
                k++;
                while (*s != '\0'  &&  *s != ')')
                  {
                   s++;
                  }
                if (*s == ')')
                  {
                   s++;
                  }
               }
            }
          p0 = (int) *s;
          k0 = k;
          while (*s == '-'  &&  k > 1)
            {
             k--;
             s++;
            }
          if (*s == '<')
            {
             s++;
            }
          if (isdigit (*s))
            {
             /****  Priorität bestimmen  ****/
             p = *s - '0';
             s++;
            }
          if (*s == '^'  &&  *(s+1) == '^')
            {
             s++;
             if ((phonet_init & CHECK_RULES)
             &&  ! isletter [wort[i+k0]])
               {
                /****  Regelprüfung mittels "check_rules"  ****/
                s = s-2;
               }
            }

          if (*s == '\0'
          || (*s == '^'  &&  (i == 0  ||  ! isletter [wort[i-1]])
           && (*(s+1) != '$'
           || (! isletter [wort[i+k0]]  &&  wort[i+k0] != '.')))
          || (*s == '$'  &&  i > 0  &&  isletter [wort[i-1]]
           && (! isletter [wort[i+k0]]  &&  wort[i+k0] != '.')))
            {



             /****  Fortsetzung suchen, falls:         ****/
             /****  k > 1  und  KEIN '-' im Suchstring ****/
             c0 = wort [i+k-1];
             n0 = phonet_hash [(int) c0 + modus];

             if (k > 1  &&  n0 >= 0
             &&  p0 != (int) '-'  &&  wort[i+k] != '\0')
               {
                /****  Forts.-Regeln zu "wort[i+k]" testen  ****/
                while (phonet_rules[n0] == NULL
                ||  phonet_rules[n0][0] == c0)
                  {
                   if (phonet_rules [n0] == NULL
                   ||  phonet_rules [n0+1+(modus/ANZ_HASH)]==NULL)
                     {
                      /****  keine Umwandlungsregel  ****/
                      n0 += 3;
                      continue;
                     }
                   if (phonet_init & DO_TRACE)
                     {
                      trace_info ("> > Forts.-Regel Nr.",
                          n0, "wird geprüft");
                     }

                   /****  ganzen String prüfen  ****/
                   k0 = k;
                   p0 = 5;
                   s = phonet_rules[n0];
                   s++;
                   while (*s != '\0'  &&  wort[i+k0] == *s
                   && ! isdigit(*s)  &&  strchr("(-<^$",*s)==NULL)
                     {
                      k0++;
                      s++;
                     }
                   if (*s == '(')
                     {
                      /****  Buchstabenbereich prüfen  ****/
                      if (isletter [wort[i+k0]]
                      &&  strchr (s+1, wort[i+k0]) != NULL)
                        {
                         k0++;
                         while (*s != '\0'  &&  *s != ')')
                           {
                            s++;
                           }
                         if (*s == ')')
                           {
                            s++;
                           }
                        }
                     }
                   while (*s == '-')
                     {
                      /****  "k0" wird NICHT vermindert  ****/
                      /****  wg. Abfrage "if (k0 == k)"  ****/
                      s++;
                     }
                   if (*s == '<')
                     {
                      s++;
                     }
                   if (isdigit (*s))
                     {
                      p0 = *s - '0';
                      s++;
                     }

                   if (*s == '\0'
                     /****  *s == '^' scheidet aus  ****/
                   || (*s == '$'  &&  ! isletter [wort[i+k0]]
                    &&  wort[i+k0] != '.'))
                     {
                      if (k0 == k)
                        {
                         /****  Dies ist nur ein Teilstring  ****/
                         if (phonet_init & DO_TRACE)
                           {
                            trace_info ("> > Forts.-Regel Nr.",
                                n0, "nicht benutzt (zu kurz)");
                           }
                         n0 += 3;
                         continue;
                        }

                      if (p0 < p)
                        {
                         /****  Priorität ist zu klein  ****/
                         if (phonet_init & DO_TRACE)
                           {
                            trace_info ("> > Forts.-Regel Nr.",
                                n0, "nicht benutzt (Priorität)");
                           }
                         n0 += 3;
                         continue;
                        }
                      /****  Regel paßt; Suche abbrechen  ****/
                      break;
                     }

                   if (phonet_init & DO_TRACE)
                     {
                      trace_info ("> > Forts.-Regel Nr.",
                          n0, "nicht benutzt");
                     }
                   n0 += 3;
                  } /****  Ende von "while"  ****/

                if (p0 >= p
                && (phonet_rules[n0] != NULL
                &&  phonet_rules[n0][0] == c0))
                  {
                   if (phonet_init & DO_TRACE)
                     {
                      trace_info ("> Regel Nr.", n,"");
                      trace_info ("> nicht benutzt wg. Forts.",
                          n0,"");
                     }
                   n += 3;
                   continue;
                  }
               }

             /****  String ersetzen  ****/

             if (phonet_init & DO_TRACE)
               {
                trace_info ("Benutzt wird Regel Nr.", n,"");
               }
             s = phonet_rules [n+1+(modus/ANZ_HASH)];
             p0 = (phonet_rules[n][0] != '\0'
                &&  strchr (phonet_rules[n]+1,'<') != NULL) ? 1:0;

             if (p0 == 1  &&  z == 0)
               {
                /****  Regel mit '<' wird angewendet  ****/
                if (j > 0  &&  *s != '\0'
                && (ziel[j-1] == c  ||  ziel[j-1] == *s))
                  {
                   j--;
                  }
                z0 = 1;
                z++;
                k0 = 0;
                while (*s != '\0'  &&  wort[i+k0] != '\0')
                  {
                   wort[i+k0] = *s;
                   k0++;
                   s++;
                  }
                if (k > k0)
                  {
                   strcpy (wort+i+k0, wort+i+k);
                  }
                if ((phonet_init & CHECK_RULES)
                &&  (*s != '\0'  ||  k < k0))
                  {
                   /****  Regelprüfung mit "check_rules":    ****/
                   /****  zu lange Ersetzungsregel gefunden  ****/
                   ziel[j] = '\0';
                   return (-200);
                  }
                /****  neuer "aktueller Buchstabe"  ****/
                c = wort[i];
               }
             else
               {
                if ((phonet_init & CHECK_RULES)
                &&  p0 == 1  &&  z > 0)
                  {
                   /****  Regelprüfung mit "check_rules":  ****/
                   /****  Rekursion liegt vor -> Fehler    ****/
                   ziel[j] = '\0';
                   return (-100);
                  }
                i = i+k-1;
                z = 0;
                while (*s != '\0'
                &&  *(s+1) != '\0'  &&  j < anz-1)
                  {
                   if (j == 0  ||  ziel[j-1] != *s)
                     {
                      ziel[j] = *s;
                      j++;
                     }
                   s++;
                  }
                /****  neuer "aktueller Buchstabe"  ****/
                c = *s;

                if (phonet_rules[n][0] != '\0'
                &&  strstr (phonet_rules[n]+1, "^^") != NULL)
                  {
                   if (c != '\0')
                     {
                      ziel[j] = c;
                      j++;
                     }
                   strcpy (wort, wort+i+1);
                   i = 0;
                   z0 = 1;
                  }
               }

             break;
            }
          n += 3;
         }
      }

    if (z0 == 0)
      {
       if (j < anz-1  &&  c != '\0'
       && (j == 0  ||  ziel[j-1] != c))
         {
          /****  nur doppelte Buchstaben verdichten  ****/
          ziel[j] = c;
          j++;
         }
       i++;
       z = 0;
      }
   }

 if (ww != hilf)
   {
    free (ww);
   }
 ziel[j] = '\0';
 return (j);
}




void check_rules (int trace_only)
/****  Alle phonetischen Regeln auf Konsistenz prüfen       ****/
/****  ("trace_only" > 0:  nur diese Regel trace'n)         ****/

/****  Die Prüfung ist im Detail sehr kompliziert und       ****/
/****  erfolgt grob skizziert ungefähr folgendermaßen:      ****/
/****  Zuerst wird jede Regel auf syntaktische Korrektheit  ****/
/****  geprüft (insbesondere Reihenfolge der Regelzusätze:  ****/
/****  Minuszeichen, '<', Priorität sowie '^' und '$').     ****/

/****  Danach wird für jede Regel der Suchstring als        ****/
/****  Testwort für "phonet" genommen und phonetisch        ****/
/****  umgewandelt. Dabei gibt es eine Sonderbehandlung     ****/
/****  für Regeln mit '(' und ')'. Für diese werden die     ****/
/****  Regeln vorübergehend modifiziert, um Einzelregeln    ****/
/****  ohne Klammern zu erhalten und als solche einzeln     ****/
/****  prüfen zu können. Beispiel:                          ****/
/****  Beim Suchstring "GS(CH)-" werden Regeln für "GSC-"   ****/
/****  und "GSH-" erzeugt und geprüft.                      ****/

/****  Um bei der Prüfung Regelkonflikte zu vermeiden,      ****/
/****  werden Suchstring und Testwort ggf. mit dem Zeichen  ****/
/****  'TEST_CHAR' umgeben. Diese Sonderbehandlung ist      ****/
/****  abhängig von '^' und '$' im Suchstring und wird      ****/
/****  teilweise von der Funktion "string_prepare"          ****/
/****  übernommen.                                          ****/

/****  Anschließend erfolgt die Umwandlung, deren Ergebnis  ****/
/****  mit dem angegebenen Ersatzstring übereinstimmen      ****/
/****  muss. Im Falle von Fehlern wurden irgendwelche       ****/
/****  Abhängigkeiten nicht richtig beachtet.               ****/
/****  Einige Sonderfälle, die bei der Prüfung Probleme     ****/
/****  Schwierigkeiten bereiten, wurden als entsprechende   ****/
/****  Sonderfälle in der Prüfung berücksichtigt.           ****/
{
 int  i,k,n;
 int  errors = 0;
 CHAR *r,*r0,rule[35];
 CHAR *s,fehler[201];
 CHAR orig[35],orig2[35];
 CHAR text[35],text2[35];

 /****  Initialisierung  ****/
 initialize_phonet();

 isletter [(int) TEST_CHAR] = 1;
 phonet_init = phonet_init | CHECK_RULES;
 i = 0;

 while (phonet_rules[i] != ENDE)
   {
    /****  Syntaxprüfung für jeden String einzeln  ****/
    if ((i/3)+1 == trace_only)
      {
       phonet_init = phonet_init | DO_TRACE;
      }
    else if (trace_only > 0)
      {
       phonet_init = phonet_init & (~DO_TRACE);
      }

    strcpy (fehler,"");
    k = 0;
    if (i % 3 == 0  &&  (phonet_rules[i] == NULL
    || (phonet_rules[i+1] == NULL  &&  phonet_rules[i+2] ==NULL)))
      {
       strcpy (fehler,"  unzulässiger NULL-Wert");
       k = -10;
      }

    if (k >= 0)
      {
       if (phonet_rules[i] == NULL)
         {
          i++;
          continue;
         }

       if (i % 3 == 0)
         {
          /****  Anfangsbuchstaben prüfen  ****/
          s = phonet_rules[i];
          n = phonet_hash [(int) *s];
          if (i >= n+3  &&  n >= 0
          &&  *s != phonet_rules[i-3][0])
            {
             strcpy (fehler,"  Anfangsbuchstabe falsch");
             k = -10;
            }
          n = phonet_hash [(int) *s + ANZ_HASH];
          if (i >= n+3  &&  n >= 0
          &&  *s != phonet_rules[i-3][0])
            {
             strcpy (fehler,"  Anfangsbuchstabe falsch");
             k = -10;
            }

          if (k >= 0)
            {
             /****  Länge des Suchbegriffs prüfen  ****/
             k = 0;
             while (*s != '\0'  &&  ! isdigit (*s)
             &&  strchr ("()<^$", *s) == NULL)
               {
                k++;
                s++;
               }
             if (k == 0)
               {
                strcpy (fehler,"  Suchbegriff ist leer");
                if (*s != '\0'  &&  strchr ("()<^$", *s) == NULL)
                  {
                   strcpy(fehler,"  Metazeichen am Stringanfang");
                  }
                k = -10;
               }
            }
         }
      }

    if (k >= 0)
      {
       /****  Syntaxprüfung für den String  ****/
       k = 0;
       s = phonet_rules[i];
       n = 0;
       if (i % 3 == 0  &&  *s != '\0')
         {
          s++;
          n++;
         }
       while (*s != '\0')
         {
          if (*s == '(')
            {
             if (k >= 1  ||  ! isletter [*(s+1)])
               {
                k = -10;
                break;
               }
             s++;
             n++;
             while (isletter[*s])
               {
                s++;
               }
             if (*s != ')')
               {
                k = -10;
                break;
               }
             k = 1;
            }
          else if (*s == '-')
            {
             /****  Hier muss "k > 2" stehen    ****/
             /****  (Mehrere '-' sind erlaubt)  ****/
             n--;
             if (k > 2  ||  n <= 0)
               {
                k = -10;
                break;
               }
             k = 2;
            }
          else if (*s == '<')
            {
             if (k >= 3)
               {
                k = -10;
                break;
               }
             k = 3;
            }
          else if (isdigit (*s))
            {
             if (k >= 4)
               {
                k = -10;
                break;
               }
             k = 4;
            }
          else if (*s == '^')
            {
             if (k >= 5)
               {
                k = -10;
                break;
               }
             if (*(s+1) == '^')
               {
                s++;
               }
             k = 5;
            }
          else if (*s == '$')
            {
             if (k >= 6  ||  *(s+1) != '\0')
               {
                k = -10;
                break;
               }
             k = 6;
            }
          else if (k > 0  ||  *s == ')')
            {
             k = -10;
             break;
            }
          else
            {
             n++;
            }
          s++;
         }

       if (k > 0  &&  i % 3 != 0)
         {
          sprintf (fehler,"  Metazeichen im Ersatzstring");
          k = -10;
         }
       else if (k < 0)
         {
          sprintf (fehler,"  Syntaxfehler im Suchstring");
         }
       else if ((int) strlen (phonet_rules[i]) > 30)
         {
          sprintf (fehler,"  String sehr lang ( > 30 Zeichen)");
          k = -1;
         }
       s = phonet_rules[i];

       if (k >= 0  &&  i % 3 == 0
       &&  n > 0  &&  strchr (s,'<') != NULL)
         {
          /****  Längen von Such- und Ersatzstrings prüfen  ****/
          if ((phonet_rules[i+1] != NULL
           &&  strcmp (s,phonet_rules[i+1]) == 0)
          || (phonet_rules[i+2] != NULL
           &&  strcmp (s,phonet_rules[i+2]) == 0))
             {
              strcpy (fehler,"  Ersetzung falsch wg. '<'");
              k = -10;
             }
          if ((phonet_rules[i+1] != NULL
           && (int) strlen (phonet_rules[i+1]) > n)
          || (phonet_rules[i+2] != NULL
           && (int) strlen (phonet_rules[i+2]) > n))
             {
              strcpy (fehler,"  Ersetzung zu lang wg. '<'");
              k = -10;
             }
         }
      }

    if (k < 0)
      {
       /****  Fehlermeldung ausgeben  ****/
       s = "Evtl. Fehler in Regel";
       if (k < -1)
         {
          s = s+6;
         }
       trace_info (s, i-(i%3), fehler);
       errors++;
      }


    if (k >= 0  &&  i % 3 != 0)
      {
       /****  Umwandlung durchführen und Ergebnis prüfen  ****/
       n = i % 3;
       r = strchr (phonet_rules[i-n], '(');
       if (r == NULL)
         {
          /****  KEIN regulärer Ausruck im Suchstring  ****/
          r = "  ";
         }
       r++;

       while (*r != ')'  &&  *r != '\0')
         {
          /****  Reguläre Ausdrücke (z.B. "GS(CH)--") in    ****/
          /****  Einzelregeln aufteilen und einzeln prüfen  ****/
          r0 = phonet_rules[i-n];
          strcpy (rule, r0);
          phonet_rules[i-n] = rule;
          s = strchr (rule,'(');

          if (s != NULL)
            {
             *s = *r;
             s++;
             while (*s != ')'  &&  *s != '\0')
               {
                strcpy (s,s+1);
               }
             if (*s == ')')
               {
                strcpy (s,s+1);
               }
            }

          /****  Prüfung durchführen  ****/
          sprintf (orig, "%c%s", TEST_CHAR, phonet_rules[i-n]);
          sprintf (orig2, "%c%s", TEST_CHAR, phonet_rules[i]);

          if (strchr (phonet_rules[i-n],'^') != NULL)
            {
             sprintf (orig, orig+1);
             sprintf (orig2,orig2+1);
            }
          if (strchr (phonet_rules[i-n],'-') != NULL
          ||  strchr (phonet_rules[i-n],'$') == NULL)
            {
             sprintf (orig, "%s%c", orig, TEST_CHAR);
             sprintf (orig2,"%s%c", orig2,TEST_CHAR);
            }
          if (orig2[0] == orig2[1]  &&  orig2[2] == '\0')
            {
             /****  z.B. orig2 == "<TEST_CHAR><TEST_CHAR>"  ****/
             orig2[1] = '\0';
            }

          /****  Ergebnis der Umwandlung prüfen  ****/
          k = phonet (orig,text, 33,n);
          if (k > -100)
            {
             k = phonet (orig2,text2, 33,n);
            }

          if (k <= -100)
            {
             /****  Fehler gefunden  ****/
             phonet_rules[i-n] = r0;
             strcpy (fehler,"  Rekursion gefunden");
             if (k == -200)
               {
                strcpy (fehler,"  Ersetzung zu lang wg. '<'");
               }
             trace_info ("Fehler in Regel", i-(i%3), fehler);
             errors++;
             break;
            }

          /****  zweite Prüfung  ****/
          if (strcmp (text,orig2) != 0)
            {
             string_prepare (fehler+80, rule,rule);
             string_prepare (fehler, orig,orig);

             phonet_rules[i-n] = fehler+80;
             (void) phonet (fehler, fehler+40, 33,n);
             phonet_rules[i-n] = rule;
             fehler[0] = '\0';
             if (strcmp (fehler+40, orig2) == 0)
               {
                strcpy (text,orig2);
               }
            }

          if (strcmp (text2,orig2) != 0
          && ((strcmp (phonet_rules[i-n],"AVIER$") == 0  &&  n==1
           &&  strcmp (phonet_rules[i],"AWIE") == 0)
          || (strcmp (phonet_rules[i-n],"GH") == 0  &&  n == 1
           &&  strcmp (phonet_rules[i],"G") == 0)
          || (strcmp (phonet_rules[i-n],"GGF.") == 0  &&  n == 1
           &&  strcmp (phonet_rules[i],"GF.") == 0)
          || (strcmp (phonet_rules[i-n],"HAVEN7$") == 0  &&  n == 1
           &&  strcmp (phonet_rules[i],"HAFN") == 0)
          || (strcmp (phonet_rules[i-n],"HEAD-") == 0  &&  n == 1
           &&  strcmp (phonet_rules[i],"HE") == 0)
          || (strcmp (phonet_rules[i-n],"IERRE$") == 0
           &&  strcmp (phonet_rules[i],"IER") == 0)
          || (strcmp (phonet_rules[i-n],"IVIER$") == 0  &&  n == 1
           &&  strcmp (phonet_rules[i],"IWIE") == 0)
          || (strcmp (phonet_rules[i-n],"SHST") == 0  &&  n == 1
           &&  strcmp (phonet_rules[i],"SHT") == 0)))
             {
              /****  Sonderfälle  ****/
              strcpy (text2, orig2);
             }

          if (strcmp (text2,orig2) != 0
          && (s = strchr (orig2,'I')) != NULL)
            {
             /****  Sonderprüfung für Ersatzstrings mit 'I'  ****/
             if (strchr (s+1,'I') != NULL)
               {
                /****  zweites 'I' nehmen, falls vorhanden  ****/
                s = strchr (s+1,'I');
               }
             *s = 'J';
             (void) phonet (orig2,text2, 33,n);
             *s = 'I';
            }

          /****  Sonderbehandlung für Strings mit '-'  ****/
          s = orig;
          k = 0;
          while (*s != '\0'  &&  ! isdigit (*s)
          &&  strchr ("-<^$",*s) == NULL)
            {
             s++;
             k++;
            }
          while (*s != '\0')
            {
             if (*s == '-')
               {
                k--;
               }
             s++;
            }

          if (strcmp (text2,orig2) != 0
           && ((strchr (orig,'-') != NULL  &&  k > 0)
          || (phonet_rules[i-n][0] == phonet_rules[i-n][1]
           &&  phonet_rules[i-n][0] == phonet_rules[i][0])
          || (strncmp (phonet_rules[i-n],"GEGEN",5) == 0
           &&  strncmp (phonet_rules[i],"GEGN",4) == 0)
          || (strncmp (phonet_rules[i-n],"AI",2) == 0
           &&  phonet_rules[i][0] == 'E'
           &&  k > 1  &&  strncmp (s-2,"E$",2) == 0)))
            {
             s = orig + k;
             k = (int) strlen (orig2);
             if (k > 0)
               {
                if (orig2[k-1] == TEST_CHAR)
                  {
                   k--;
                  }
                strcpy (fehler+1,orig2);
                strcpy (fehler+1+k,s);
                k = 1;

                if (phonet_rules[i-n][0] == phonet_rules[i-n][1]
                &&  phonet_rules[i-n][0] == phonet_rules[i][0]
                &&  phonet_rules[i][1] == '\0')
                  {
                   /****  Sonderprüfung doppelte Buchstaben  ****/
                   fehler[0] = TEST_CHAR;
                   fehler[1] = phonet_rules[i][0];
                   k = 0;
                  }
                if (strncmp (phonet_rules[i],"GEGN",5) == 0)
                  {
                   /****  Sonderfall "GEGEN"  ****/
                   strncpy (fehler, phonet_rules[i],3);
                   fehler[3] = 'E';
                   k = 0;
                  }
                if (phonet_rules[i-n][0] == 'H'
                &&  phonet_rules[i-n][1] != '\0'
                &&  phonet_rules[i-n][2] == 'H'
                &&  phonet_rules[i-n][1] == phonet_rules[i][0]
                &&  phonet_rules[i-n][2] == phonet_rules[i][1])
                  {
                   /****  Sonderfall "H?H"  ****/
                   fehler[0] = TEST_CHAR;
                   fehler[1] = 'H';
                   k = 0;
                  }
                if (strncmp (phonet_rules[i-n],"LV",2) == 0
                &&  strncmp (phonet_rules[i], "LW",2) == 0)
                  {
                   /****  Sonderfall "LV*"  ****/
                   fehler[3] = 'V';
                  }
                if (strncmp (phonet_rules[i-n],"AI",2) == 0
                &&  phonet_rules[i][0] == 'E')
                  {
                   /****  Sonderfall "AI*E$"  ****/
                   fehler[0] = TEST_CHAR;
                   fehler[1] = fehler[2];
                   strcpy (fehler+2, phonet_rules[i]);
                   k = 0;
                  }

                (void) phonet (fehler+k, fehler+40, 33,n);

                if (strcmp (fehler+40, orig2) != 0)
                  {
                   string_prepare (fehler+80, fehler+k,rule);
                   string_prepare (fehler, rule,rule);

                   phonet_rules[i-n] = fehler;
                   (void) phonet (fehler+80, fehler+40, 33,n);
                   phonet_rules[i-n] = rule;
                  }
                fehler[0] = '\0';
                if (strcmp (fehler+40, orig2) == 0)
                  {
                   strcpy (text2, orig2);
                  }
               }
            }

          phonet_rules[i-n] = r0;

          if (strcmp (text, orig2) != 0
          ||  strcmp (text2,orig2) != 0)
            {
             orig[0] = '\0';
             if (*r != ' ')
               {
                sprintf (orig," zu '%c'", *r);
               }
             sprintf (fehler, "  Ergebnis %d%s: \"%s\"%s\"",
                 n,orig, text,text2);

             /****  'TEST_CHAR' aus "fehler"-String löschen  ****/
             s = fehler;
             while (*s != '\0')
               {
                while (*s == TEST_CHAR)
                  {
                   strcpy (s,s+1);
                  }
                s++;
               }

             /****  Fehlermeldung ausgeben  ****/
             s = "Evtl. Fehler in Regel";
             if (strcmp (text,orig2) != 0)
               {
                s = s+6;
               }
             trace_info (s, i-(i%3), fehler);
             errors++;
            }
          r++;
         }
      }
    i++;
   }

 if (i % 3 != 0)
   {
    printf ("Fehler: Stringanzahl ist kein Vielfaches von 3.\n");
    errors++;
   }
 isletter [(int) TEST_CHAR] = 0;
 phonet_init = phonet_init & (~CHECK_RULES);

 printf ("Prüfung der phonetischen Regeln:  ");
 if (errors == 0)
   {
    printf ("Kein syntaktischer Fehler feststellbar.\n");
   }
 else
   {
    printf ("Insgesamt %d Fehler gefunden.\n\n", errors);
    printf ("Anmerkungen:\n");
    printf ("a) Die korrekte Syntax für Suchstrings lautet:\n");
    printf ("      <wort> [<->..] [<] [<0-9>] [^[^]] [$]\n");
    printf ("   Am Ende von \"wort\" darf optional als");
    printf (" regulärer Ausdruck EIN Array\n   von Buchstaben");
    printf (" stehen, das in '(' und ')' eingeschlossen sein");
    printf (" muss.\n");
    printf ("b) Bei Regeln mit '<' muss der Ersatzstring genau");
    printf ("so lang oder kürzer\n   als das Suchwort sein.\n");
    printf ("c) Die Reihenfolge der Regeln gibt gleichzeitig");
    printf (" eine Priorität an.\n   Daher müssen z.B. die");
    printf (" Regeln für \"SCH\" vor den Regeln für \"S\"\n");
    printf ("   stehen (sonst Umwandlungsfehler bei \"SCH\").\n");
    printf ("d) Eine weitere Fehlerquelle liegt im");
    printf (" Nicht-Beachten von Abhängigkeiten.\n");
    printf ("   So wäre etwa \"NJE\" als Ersatzstring falsch,");
    printf (" weil das 'J' gemäss einer\n   anderen Regel");
    printf (" ebenfalls umgewandelt werden müsste.\n");
   }
}



void main (int argc, char *argv[])
{
  char text[101];
  int  i = -1;

  if (argc < 2)
    {
     printf ("Aufruf:  phonet  <wort>       [ -trace ]\n");
     printf (" oder :  phonet -check_rules  [ -trace");
     printf (" [<regel_nr>] ]\n\n");
     printf ("Programm für phonetische Stringumwandlung\n\n");
     printf ("Optionen:\n");
     printf ("-check_rules :  Phonetische Regeln prüfen\n");
     printf ("-trace       :  Ausführliches Logging ausgeben.");
     printf (" Ist bei \"-check_rules\"\n                ");
     printf ("zusätzl. eine Regel-Nr. angegeben, wird nur für ");
     printf ("diese\n                das Logging durchgeführt\n");
     return;
    }

  if (argc >= 3  &&  strcmp (argv[2], "-trace") == 0)
    {
     if (argc >= 4  &&  atoi (argv[3]) > 0)
       {
        i = atoi (argv[3]);
       }
     phonet_init = phonet_init | DO_TRACE;
    }
  if (strcmp (argv[1], "-check_rules") == 0)
    {
     check_rules (i);
     return;
    }

  strcpy (text,"               ");
  if ((int) strlen (argv[1]) > 100)
    {
     strcpy (text, "(gekürzt wg. Überlänge)");
     argv[1][100] = '\0';
    }
  printf ("Ausgangswort %s:  \"%s\"\n\n", text, argv[1]);

  phonet (argv[1],text,101,1);
  printf ("Umwandlung nach 1. Regelsatz:  \"%s\"\n", text);

  phonet (argv[1],text,101,2);
  printf ("Umwandlung nach 2. Regelsatz:  \"%s\"\n", text);
}


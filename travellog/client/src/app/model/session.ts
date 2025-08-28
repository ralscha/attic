export interface Session {
  id: string;
  loggedIn: boolean;
  lastAccess: number;
  ip: string;
  userAgent: string;
  ua: { uaBrowser: string, uaOs: string, uaDevice: string };
}

export interface User {
  id: string;
  email: string;
  lastAccess: number;
  authority: string;
  enabled: boolean;
  expired: boolean;
  admin: boolean;
}

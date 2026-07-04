export type LoginRequest = {
  email: string | null;
  password: string | null;
};

export interface RegisterRequest {
  firstName: string;
  familyName: string;
  email: string;
  password: string;
}

export interface AuthResponse {
  encrpytedId: string;
  email: string;
  role: 'USER' | 'ADMIN';
  token: string;
}

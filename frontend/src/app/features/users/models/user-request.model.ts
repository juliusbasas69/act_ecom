export interface UserRequest {
  firstName: string;
  familyName: string;
  email: string;
  password: string | null;
  confirmPassword: string | null;
  role: string;
}

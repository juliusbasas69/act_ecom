export interface UserCreateRequest {
  firstName: string;
  familyName: string;
  email: string;
  password: string;
  confirmPassword: string;
  role: string;
}

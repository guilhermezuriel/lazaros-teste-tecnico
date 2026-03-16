import { Profile } from './profile.model';

export interface User {
  id: string;
  nome: string;
  perfis: Profile[];
}

export interface UserRequest {
  nome: string;
  perfis: string[];
}

import { IUser } from 'app/shared/model/user.model';

export interface ICadastroUser {
  id?: number;
  nome?: string | null;
  telefone?: string | null;
  tipo?: string | null;
  pais?: string | null;
  estado?: string | null;
  cidade?: string | null;
  bairro?: string | null;
  cep?: number | null;
  logradouro?: string | null;
  numero?: number | null;
  complemento?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<ICadastroUser> = {};

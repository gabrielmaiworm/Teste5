import { IUser } from 'app/shared/model/user.model';
import { IAcao } from 'app/shared/model/acao.model';
import { ICadastroDoacao } from 'app/shared/model/cadastro-doacao.model';
import { ISolicitacao } from 'app/shared/model/solicitacao.model';

export interface ILigacao {
  id?: number;
  user?: IUser | null;
  acao?: IAcao | null;
  cadastroDoacaos?: ICadastroDoacao[] | null;
  solicitacaos?: ISolicitacao[] | null;
}

export const defaultValue: Readonly<ILigacao> = {};

import dayjs from 'dayjs';
import { ICadastroDoacao } from 'app/shared/model/cadastro-doacao.model';
import { ISolicitacao } from 'app/shared/model/solicitacao.model';
import { ILigacao } from 'app/shared/model/ligacao.model';

export interface IAcao {
  id?: number;
  dataAcao?: string | null;
  usuarioCriacaoAcao?: string | null;
  pendente?: boolean | null;
  dataExecucaoAcao?: string | null;
  ativa?: boolean | null;
  observacoes?: string | null;
  cadastroDoacao?: ICadastroDoacao | null;
  solicitacao?: ISolicitacao | null;
  ligacao?: ILigacao | null;
}

export const defaultValue: Readonly<IAcao> = {
  pendente: false,
  ativa: false,
};

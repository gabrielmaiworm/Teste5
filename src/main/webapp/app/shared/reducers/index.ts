import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import cadastroUser from 'app/entities/cadastro-user/cadastro-user.reducer';
// prettier-ignore
import ligacao from 'app/entities/ligacao/ligacao.reducer';
// prettier-ignore
import item from 'app/entities/item/item.reducer';
// prettier-ignore
import cadastroDoacao from 'app/entities/cadastro-doacao/cadastro-doacao.reducer';
// prettier-ignore
import solicitacao from 'app/entities/solicitacao/solicitacao.reducer';
// prettier-ignore
import acao from 'app/entities/acao/acao.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  cadastroUser,
  ligacao,
  item,
  cadastroDoacao,
  solicitacao,
  acao,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;

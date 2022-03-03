import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CadastroUser from './cadastro-user';
import Ligacao from './ligacao';
import Item from './item';
import CadastroDoacao from './cadastro-doacao';
import Solicitacao from './solicitacao';
import Acao from './acao';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}cadastro-user`} component={CadastroUser} />
      <ErrorBoundaryRoute path={`${match.url}ligacao`} component={Ligacao} />
      <ErrorBoundaryRoute path={`${match.url}item`} component={Item} />
      <ErrorBoundaryRoute path={`${match.url}cadastro-doacao`} component={CadastroDoacao} />
      <ErrorBoundaryRoute path={`${match.url}solicitacao`} component={Solicitacao} />
      <ErrorBoundaryRoute path={`${match.url}acao`} component={Acao} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;

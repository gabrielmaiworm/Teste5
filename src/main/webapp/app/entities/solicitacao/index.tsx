import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Solicitacao from './solicitacao';
import SolicitacaoDetail from './solicitacao-detail';
import SolicitacaoUpdate from './solicitacao-update';
import SolicitacaoDeleteDialog from './solicitacao-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SolicitacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SolicitacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SolicitacaoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Solicitacao} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SolicitacaoDeleteDialog} />
  </>
);

export default Routes;

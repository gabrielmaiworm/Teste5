import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Ligacao from './ligacao';
import LigacaoDetail from './ligacao-detail';
import LigacaoUpdate from './ligacao-update';
import LigacaoDeleteDialog from './ligacao-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LigacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LigacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LigacaoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Ligacao} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LigacaoDeleteDialog} />
  </>
);

export default Routes;

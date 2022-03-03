import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Acao from './acao';
import AcaoDetail from './acao-detail';
import AcaoUpdate from './acao-update';
import AcaoDeleteDialog from './acao-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AcaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AcaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AcaoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Acao} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AcaoDeleteDialog} />
  </>
);

export default Routes;

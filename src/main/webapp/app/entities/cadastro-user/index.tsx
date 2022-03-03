import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CadastroUser from './cadastro-user';
import CadastroUserDetail from './cadastro-user-detail';
import CadastroUserUpdate from './cadastro-user-update';
import CadastroUserDeleteDialog from './cadastro-user-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CadastroUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CadastroUserUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CadastroUserDetail} />
      <ErrorBoundaryRoute path={match.url} component={CadastroUser} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CadastroUserDeleteDialog} />
  </>
);

export default Routes;

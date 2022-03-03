import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CadastroDoacao from './cadastro-doacao';
import CadastroDoacaoDetail from './cadastro-doacao-detail';
import CadastroDoacaoUpdate from './cadastro-doacao-update';
import CadastroDoacaoDeleteDialog from './cadastro-doacao-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CadastroDoacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CadastroDoacaoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CadastroDoacaoDetail} />
      <ErrorBoundaryRoute path={match.url} component={CadastroDoacao} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CadastroDoacaoDeleteDialog} />
  </>
);

export default Routes;

import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './cadastro-user.reducer';
import { ICadastroUser } from 'app/shared/model/cadastro-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CadastroUser = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const cadastroUserList = useAppSelector(state => state.cadastroUser.entities);
  const loading = useAppSelector(state => state.cadastroUser.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="cadastro-user-heading" data-cy="CadastroUserHeading">
        Cadastro Users
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Cadastro User
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cadastroUserList && cadastroUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Telefone</th>
                <th>Tipo</th>
                <th>Pais</th>
                <th>Estado</th>
                <th>Cidade</th>
                <th>Bairro</th>
                <th>Cep</th>
                <th>Logradouro</th>
                <th>Numero</th>
                <th>Complemento</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cadastroUserList.map((cadastroUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${cadastroUser.id}`} color="link" size="sm">
                      {cadastroUser.id}
                    </Button>
                  </td>
                  <td>{cadastroUser.nome}</td>
                  <td>{cadastroUser.telefone}</td>
                  <td>{cadastroUser.tipo}</td>
                  <td>{cadastroUser.pais}</td>
                  <td>{cadastroUser.estado}</td>
                  <td>{cadastroUser.cidade}</td>
                  <td>{cadastroUser.bairro}</td>
                  <td>{cadastroUser.cep}</td>
                  <td>{cadastroUser.logradouro}</td>
                  <td>{cadastroUser.numero}</td>
                  <td>{cadastroUser.complemento}</td>
                  <td>{cadastroUser.user ? cadastroUser.user.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${cadastroUser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${cadastroUser.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${cadastroUser.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Cadastro Users found</div>
        )}
      </div>
    </div>
  );
};

export default CadastroUser;

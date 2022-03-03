import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './cadastro-doacao.reducer';
import { ICadastroDoacao } from 'app/shared/model/cadastro-doacao.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CadastroDoacao = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const cadastroDoacaoList = useAppSelector(state => state.cadastroDoacao.entities);
  const loading = useAppSelector(state => state.cadastroDoacao.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="cadastro-doacao-heading" data-cy="CadastroDoacaoHeading">
        Cadastro Doacaos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Cadastro Doacao
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {cadastroDoacaoList && cadastroDoacaoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Doacao Anonima</th>
                <th>Realiza Entrega</th>
                <th>Data Doacao</th>
                <th>Logradouro</th>
                <th>Numero</th>
                <th>Bairro</th>
                <th>Cidade</th>
                <th>Cep</th>
                <th>Estado</th>
                <th>Pais</th>
                <th>Complemento</th>
                <th>Ligacao</th>
                <th>Descricao</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cadastroDoacaoList.map((cadastroDoacao, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${cadastroDoacao.id}`} color="link" size="sm">
                      {cadastroDoacao.id}
                    </Button>
                  </td>
                  <td>{cadastroDoacao.doacaoAnonima ? 'true' : 'false'}</td>
                  <td>{cadastroDoacao.realizaEntrega ? 'true' : 'false'}</td>
                  <td>
                    {cadastroDoacao.dataDoacao ? (
                      <TextFormat type="date" value={cadastroDoacao.dataDoacao} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{cadastroDoacao.logradouro}</td>
                  <td>{cadastroDoacao.numero}</td>
                  <td>{cadastroDoacao.bairro}</td>
                  <td>{cadastroDoacao.cidade}</td>
                  <td>{cadastroDoacao.cep}</td>
                  <td>{cadastroDoacao.estado}</td>
                  <td>{cadastroDoacao.pais}</td>
                  <td>{cadastroDoacao.complemento}</td>
                  <td>
                    {cadastroDoacao.ligacao ? <Link to={`ligacao/${cadastroDoacao.ligacao.id}`}>{cadastroDoacao.ligacao.id}</Link> : ''}
                  </td>
                  <td>
                    {cadastroDoacao.descricao ? <Link to={`item/${cadastroDoacao.descricao.id}`}>{cadastroDoacao.descricao.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${cadastroDoacao.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${cadastroDoacao.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${cadastroDoacao.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Cadastro Doacaos found</div>
        )}
      </div>
    </div>
  );
};

export default CadastroDoacao;

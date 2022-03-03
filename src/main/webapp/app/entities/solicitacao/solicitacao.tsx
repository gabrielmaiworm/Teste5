import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './solicitacao.reducer';
import { ISolicitacao } from 'app/shared/model/solicitacao.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Solicitacao = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const solicitacaoList = useAppSelector(state => state.solicitacao.entities);
  const loading = useAppSelector(state => state.solicitacao.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="solicitacao-heading" data-cy="SolicitacaoHeading">
        Solicitacaos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Solicitacao
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {solicitacaoList && solicitacaoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Solicitante</th>
                <th>Anonima</th>
                <th>Data Solicitacao</th>
                <th>Endereco Entrega</th>
                <th>Local De Entrega</th>
                <th>Data Aprovacao</th>
                <th>Aprovado</th>
                <th>Ativa</th>
                <th>Ligacao</th>
                <th>Descricao</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {solicitacaoList.map((solicitacao, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${solicitacao.id}`} color="link" size="sm">
                      {solicitacao.id}
                    </Button>
                  </td>
                  <td>{solicitacao.solicitante}</td>
                  <td>{solicitacao.anonima ? 'true' : 'false'}</td>
                  <td>
                    {solicitacao.dataSolicitacao ? (
                      <TextFormat type="date" value={solicitacao.dataSolicitacao} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{solicitacao.enderecoEntrega}</td>
                  <td>{solicitacao.localDeEntrega}</td>
                  <td>
                    {solicitacao.dataAprovacao ? (
                      <TextFormat type="date" value={solicitacao.dataAprovacao} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{solicitacao.aprovado ? 'true' : 'false'}</td>
                  <td>{solicitacao.ativa ? 'true' : 'false'}</td>
                  <td>{solicitacao.ligacao ? <Link to={`ligacao/${solicitacao.ligacao.id}`}>{solicitacao.ligacao.id}</Link> : ''}</td>
                  <td>{solicitacao.descricao ? <Link to={`item/${solicitacao.descricao.id}`}>{solicitacao.descricao.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${solicitacao.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${solicitacao.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${solicitacao.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Solicitacaos found</div>
        )}
      </div>
    </div>
  );
};

export default Solicitacao;

import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './acao.reducer';
import { IAcao } from 'app/shared/model/acao.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Acao = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const acaoList = useAppSelector(state => state.acao.entities);
  const loading = useAppSelector(state => state.acao.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="acao-heading" data-cy="AcaoHeading">
        Acaos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Acao
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {acaoList && acaoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Data Acao</th>
                <th>Usuario Criacao Acao</th>
                <th>Pendente</th>
                <th>Data Execucao Acao</th>
                <th>Ativa</th>
                <th>Observacoes</th>
                <th>Cadastro Doacao</th>
                <th>Solicitacao</th>
                <th>Ligacao</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {acaoList.map((acao, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${acao.id}`} color="link" size="sm">
                      {acao.id}
                    </Button>
                  </td>
                  <td>{acao.dataAcao ? <TextFormat type="date" value={acao.dataAcao} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{acao.usuarioCriacaoAcao}</td>
                  <td>{acao.pendente ? 'true' : 'false'}</td>
                  <td>
                    {acao.dataExecucaoAcao ? <TextFormat type="date" value={acao.dataExecucaoAcao} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{acao.ativa ? 'true' : 'false'}</td>
                  <td>{acao.observacoes}</td>
                  <td>
                    {acao.cadastroDoacao ? <Link to={`cadastro-doacao/${acao.cadastroDoacao.id}`}>{acao.cadastroDoacao.id}</Link> : ''}
                  </td>
                  <td>{acao.solicitacao ? <Link to={`solicitacao/${acao.solicitacao.id}`}>{acao.solicitacao.id}</Link> : ''}</td>
                  <td>{acao.ligacao ? <Link to={`ligacao/${acao.ligacao.id}`}>{acao.ligacao.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${acao.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${acao.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${acao.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Acaos found</div>
        )}
      </div>
    </div>
  );
};

export default Acao;

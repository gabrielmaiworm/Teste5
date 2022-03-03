import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './solicitacao.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SolicitacaoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const solicitacaoEntity = useAppSelector(state => state.solicitacao.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="solicitacaoDetailsHeading">Solicitacao</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{solicitacaoEntity.id}</dd>
          <dt>
            <span id="solicitante">Solicitante</span>
          </dt>
          <dd>{solicitacaoEntity.solicitante}</dd>
          <dt>
            <span id="anonima">Anonima</span>
          </dt>
          <dd>{solicitacaoEntity.anonima ? 'true' : 'false'}</dd>
          <dt>
            <span id="dataSolicitacao">Data Solicitacao</span>
          </dt>
          <dd>
            {solicitacaoEntity.dataSolicitacao ? (
              <TextFormat value={solicitacaoEntity.dataSolicitacao} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="enderecoEntrega">Endereco Entrega</span>
          </dt>
          <dd>{solicitacaoEntity.enderecoEntrega}</dd>
          <dt>
            <span id="localDeEntrega">Local De Entrega</span>
          </dt>
          <dd>{solicitacaoEntity.localDeEntrega}</dd>
          <dt>
            <span id="dataAprovacao">Data Aprovacao</span>
          </dt>
          <dd>
            {solicitacaoEntity.dataAprovacao ? (
              <TextFormat value={solicitacaoEntity.dataAprovacao} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="aprovado">Aprovado</span>
          </dt>
          <dd>{solicitacaoEntity.aprovado ? 'true' : 'false'}</dd>
          <dt>
            <span id="ativa">Ativa</span>
          </dt>
          <dd>{solicitacaoEntity.ativa ? 'true' : 'false'}</dd>
          <dt>Ligacao</dt>
          <dd>{solicitacaoEntity.ligacao ? solicitacaoEntity.ligacao.id : ''}</dd>
          <dt>Descricao</dt>
          <dd>{solicitacaoEntity.descricao ? solicitacaoEntity.descricao.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/solicitacao" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/solicitacao/${solicitacaoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default SolicitacaoDetail;

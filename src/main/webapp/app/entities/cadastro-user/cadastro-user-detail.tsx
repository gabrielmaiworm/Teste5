import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './cadastro-user.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CadastroUserDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const cadastroUserEntity = useAppSelector(state => state.cadastroUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cadastroUserDetailsHeading">CadastroUser</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{cadastroUserEntity.id}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{cadastroUserEntity.nome}</dd>
          <dt>
            <span id="telefone">Telefone</span>
          </dt>
          <dd>{cadastroUserEntity.telefone}</dd>
          <dt>
            <span id="tipo">Tipo</span>
          </dt>
          <dd>{cadastroUserEntity.tipo}</dd>
          <dt>
            <span id="pais">Pais</span>
          </dt>
          <dd>{cadastroUserEntity.pais}</dd>
          <dt>
            <span id="estado">Estado</span>
          </dt>
          <dd>{cadastroUserEntity.estado}</dd>
          <dt>
            <span id="cidade">Cidade</span>
          </dt>
          <dd>{cadastroUserEntity.cidade}</dd>
          <dt>
            <span id="bairro">Bairro</span>
          </dt>
          <dd>{cadastroUserEntity.bairro}</dd>
          <dt>
            <span id="cep">Cep</span>
          </dt>
          <dd>{cadastroUserEntity.cep}</dd>
          <dt>
            <span id="logradouro">Logradouro</span>
          </dt>
          <dd>{cadastroUserEntity.logradouro}</dd>
          <dt>
            <span id="numero">Numero</span>
          </dt>
          <dd>{cadastroUserEntity.numero}</dd>
          <dt>
            <span id="complemento">Complemento</span>
          </dt>
          <dd>{cadastroUserEntity.complemento}</dd>
          <dt>User</dt>
          <dd>{cadastroUserEntity.user ? cadastroUserEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cadastro-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cadastro-user/${cadastroUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CadastroUserDetail;

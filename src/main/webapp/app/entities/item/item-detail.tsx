import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './item.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ItemDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const itemEntity = useAppSelector(state => state.item.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="itemDetailsHeading">Item</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{itemEntity.id}</dd>
          <dt>
            <span id="descricao">Descricao</span>
          </dt>
          <dd>{itemEntity.descricao}</dd>
          <dt>
            <span id="imagem">Imagem</span>
          </dt>
          <dd>
            {itemEntity.imagem ? (
              <div>
                {itemEntity.imagemContentType ? (
                  <a onClick={openFile(itemEntity.imagemContentType, itemEntity.imagem)}>
                    <img src={`data:${itemEntity.imagemContentType};base64,${itemEntity.imagem}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {itemEntity.imagemContentType}, {byteSize(itemEntity.imagem)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="quantidade">Quantidade</span>
          </dt>
          <dd>{itemEntity.quantidade}</dd>
          <dt>
            <span id="categoriaItem">Categoria Item</span>
          </dt>
          <dd>{itemEntity.categoriaItem}</dd>
          <dt>
            <span id="unidadeMedida">Unidade Medida</span>
          </dt>
          <dd>{itemEntity.unidadeMedida}</dd>
        </dl>
        <Button tag={Link} to="/item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/item/${itemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ItemDetail;

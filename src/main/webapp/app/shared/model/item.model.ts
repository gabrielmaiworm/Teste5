import { CategoriaItem } from 'app/shared/model/enumerations/categoria-item.model';
import { UnidadeMedida } from 'app/shared/model/enumerations/unidade-medida.model';

export interface IItem {
  id?: number;
  descricao?: string | null;
  imagemContentType?: string | null;
  imagem?: string | null;
  quantidade?: number | null;
  categoriaItem?: CategoriaItem | null;
  unidadeMedida?: UnidadeMedida | null;
}

export const defaultValue: Readonly<IItem> = {};

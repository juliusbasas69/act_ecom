import { Pagination } from './pagination.model';

export interface PagedResponse<T> {
  content: T[];
  pagination: Pagination;
}

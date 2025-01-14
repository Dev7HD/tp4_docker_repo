export interface Page<T> {
  content: T[]; // Data content
  totalElements: number; // Total elements
  totalPages: number; // Total pages
  size: number; // Elements count per page
  number: number; // Current page index
}

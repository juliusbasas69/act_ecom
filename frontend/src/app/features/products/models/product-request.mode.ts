export interface ProductRequest {
  isFeatured: boolean;
  productCode: string;
  productName: string;
  description: string;
  category: string;
  price: number;
  quantity: number;
  status: string;
  image: File | null;
}

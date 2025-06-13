export interface Pagamento {
  id?: number;
  valor: number;
  formaPagamento: string;
  dataPagamento: string;
  ordemServicoId: number;
}

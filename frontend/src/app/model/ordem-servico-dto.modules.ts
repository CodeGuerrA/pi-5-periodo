export interface OrdemServicoDTO {
  descricaoProblema: string;
  status: string;
  clienteId: number;
  veiculoId: number;
  itemPecaIds: number[];
  quantidadePecas: number[];
  servicoIds: number[];
}

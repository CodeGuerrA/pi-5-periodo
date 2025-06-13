export interface Veiculo {
  id?: number;
  marca: string;
  modelo: string;
  placa: string;
  ano: number;
  clienteId: number; // referência ao dono do veículo
}

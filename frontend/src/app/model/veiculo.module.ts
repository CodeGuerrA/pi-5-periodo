export type TipoVeiculos = 'CARRO' | 'MOTO'; 

export interface Veiculo {
  id?: number;
  placa: string;
  modelo: string;
  ano: string;         
  cor: string;
  tipo: TipoVeiculos;
  clienteId: number;   
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Funcionario } from '../model/funcionario.module';

@Injectable({ providedIn: 'root' })
export class FuncionarioService {
  private apiUrl = 'http://localhost:8080/funcionarios';

  constructor(private http: HttpClient) {}

  getFuncionarios(): Observable<Funcionario[]> {
    return this.http.get<Funcionario[]>(this.apiUrl);
  }

  getFuncionarioById(id: number): Observable<Funcionario> {
    return this.http.get<Funcionario>(`${this.apiUrl}/${id}`);
  }

  createFuncionario(func: Funcionario): Observable<Funcionario> {
    return this.http.post<Funcionario>(this.apiUrl, func);
  }

  updateFuncionario(id: number, func: Funcionario): Observable<Funcionario> {
    return this.http.put<Funcionario>(`${this.apiUrl}/${id}`, func);
  }

  deleteFuncionario(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

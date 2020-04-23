import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Elevator } from '../models/elevator.model';

@Injectable({
  providedIn: 'root'
})
export class ElevatorService {

  constructor(private http: HttpClient) { }

  fetchElevators(): Observable<Elevator[]> {
    return this.http.get<Elevator[]>('/rest/v1/elevators');
  }

  requestElevator(toFloor: number): Observable<Elevator> {
    return this.http.post<Elevator>(`/rest/v1/elevators/${toFloor}`, null);
  }
}

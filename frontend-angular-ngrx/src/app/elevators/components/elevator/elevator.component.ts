import { Component, OnInit, Input, ChangeDetectionStrategy } from '@angular/core';
import { Elevator } from '../../models/elevator.model';

@Component({
  selector: 'elev-elevator',
  templateUrl: './elevator.component.html',
  styleUrls: ['./elevator.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ElevatorComponent implements OnInit {

  @Input()
  elevator: Elevator;

  constructor() { }

  ngOnInit() {
  }

}

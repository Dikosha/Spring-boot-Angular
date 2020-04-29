import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-reserve',
  templateUrl: './reserve.component.html',
  styleUrls: ['./reserve.component.css']
})
export class ReserveComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {

    $('.dateselect').datepicker({
      format: 'mm.dd.yyyy',
      // startDate: '-3d'
    });
  }

}

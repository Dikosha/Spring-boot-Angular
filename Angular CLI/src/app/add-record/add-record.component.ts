import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-add-record',
  templateUrl: './add-record.component.html',
  styleUrls: ['./add-record.component.css']
})
export class AddRecordComponent implements OnInit {

  constructor(private http: HttpClient) { }

  /*model: AddRecordViewModel = {
    email: '',
    phone: '',
    password: '',
    re_password: ''
  }; */
  emailBusy = false;
  isRegistered = false;

  ngOnInit(): void {
  }



}

export interface AddRecordViewModel {
  service: number;
  client: number;
  master: number;
  date: string;

}


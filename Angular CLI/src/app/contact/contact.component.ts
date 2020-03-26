import { Component, OnInit } from '@angular/core';
import {ScriptLoadingService} from '../_services/script-loading.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  constructor(private scriptLoadingService: ScriptLoadingService) { }

  ngOnInit(): void {
    this.scriptLoadingService.loadScript();
  }

}

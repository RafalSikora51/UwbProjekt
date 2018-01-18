import { Component, OnInit, Input } from '@angular/core';
import { CalendarService } from '../calendar/calendar.service';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldControl } from '@angular/material/form-field/';
import { MAT_MOMENT_DATE_FORMATS, MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css'],
  providers: [
    
    { provide: MAT_DATE_LOCALE, useValue: 'pl-PL' },
  ],
  

})



export class CalendarComponent implements OnInit {

  model: any = {};
  showHours: boolean;

  constructor(
    private adapter: DateAdapter<any>, 
    private toastr: ToastrService, 
    private calendarService: CalendarService,
   ) { }
  ngOnInit() {
    this.showHours = false;
    this.adapter.setLocale('pl');
  }
  showInput() {
    // console.log(this.model.abc === undefined);
    // console.log(this.model.abc);
    // console.log(this.model.abc.toDateString());
    // console.log(this.model.abc.toLocaleDateString());
    // console.log(this.model.abc.toLocaleFormat());
    // console.log(this.model.abc.toLocaleString());
    // console.log(this.model.abc.toLocaleTimeString());
  }

  showHoursEnable() {
    if (this.model.abc !== undefined) {
      this.showHours = !this.showHours;
      console.log('pokazuje godziny');
      console.log(this.showHours);
    }
    else {
      this.toastr.error('Zanim zostaną wyświetlone dostępne godziny, wybierz dzień!');
    }
  }



}

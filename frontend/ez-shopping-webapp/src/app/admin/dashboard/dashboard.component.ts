import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { switchMap } from 'rxjs/operators';
import { SystemCPU } from 'src/app/Model/system-cpu';
import { SystemlHealth } from 'src/app/Model/system-health';
import { DashboardService } from 'src/app/services/admin/dashboard.service';
import { Subscription, timer } from "rxjs";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit, OnDestroy {
  tracesList: any[] = [];
  selectedTrace: any;
  systemHealth!: SystemlHealth;
  systemCPU!: SystemCPU;
  processUptime!: String;
  http200Traces: any[] = [];
  http400Traces: any[] = [];
  http404Traces: any[] = [];
  http500Traces: any[] = [];
  httpDefaultTraces: any[] = [];
  timeStamp!: number;
  viewTrace = false;

  subscription!: Subscription;

  constructor(private dashboardService: DashboardService) { }

  ngOnInit(): void {
    this.viewTrace = false;
    this.getTraces();
    this.getCpuUsage();
    this.getSystemHealth();
    this.getProcessUpTime(false);

    /*this.subscription = timer(0, 10000).pipe(
      switchMap(async () => this.onRefreshData())
    ).subscribe()*/
  }

  ngOnDestroy(): void {
    /*this.subscription.unsubscribe();*/
  }

  private getCpuUsage(): void {
    this.dashboardService.getSystemCPU().subscribe(
      (response: SystemCPU) => {
        //console.log(response);
        this.systemCPU = response;
      },
      (error: HttpErrorResponse) => {
        //alert(error.message);
      }
    );
  }

  private getSystemHealth(): void {
    this.dashboardService.getSystemHealth().subscribe(
      (response: SystemlHealth) => {
        //console.log(response);
        this.systemHealth = response;
        this.systemHealth.components.diskSpace.details.free = this.formatBytes(this.systemHealth.components.diskSpace.details.free);
      },
      (error: HttpErrorResponse) => {
        //alert(error.message);
      }
    );
  }

  private getProcessUpTime(isUpdateTime: boolean): void {
    this.dashboardService.getProcessUptime().subscribe(
      (response: any) => {
        //console.log(response);
        this.timeStamp = Math.round(response.measurements[0].value);
        this.processUptime = this.formateUptime(this.timeStamp);
        if (isUpdateTime) {
          this.updateTime();
        }

      },
      (error: HttpErrorResponse) => {
        //alert(error.message);
      }
    );
  }
  private getTraces(): void {
    this.dashboardService.getHttpTraces().subscribe(
      (response: any) => {
        //console.log(response.traces);
        this.processTraces(response.traces);
      },
      (error: HttpErrorResponse) => {
        //alert(error.message);
      }
    );
  }

  private processTraces(traces: any): void {
    this.tracesList = traces;
    this.tracesList.forEach(trace => {
      switch (trace.response.status) {
        case 200:
          this.http200Traces.push(trace);
          break;
        case 400:
          this.http400Traces.push(trace);
          break;
        case 500:
          this.http404Traces.push(trace);
          break;
        case 404:
          this.http404Traces.push(trace);
          break;
        default:
          this.httpDefaultTraces.push(trace);
          break;
      }
    });
  }

  onRefreshData() {
    this.http200Traces = [];
    this.http400Traces = [];
    this.http404Traces = [];
    this.http500Traces = [];
    this.getTraces();
    this.getCpuUsage();
    this.getSystemHealth();
    this.getProcessUpTime(false);
  }

  onSelectTrace(trace: any) {
    this.selectedTrace = trace;
    this.viewTrace = true;

  }

  closeTrace() {
    this.selectedTrace = undefined;
    this.viewTrace = false;

  }

  formatBytes(bytes: any): string {
    if (bytes === 0) {
      return "0 Bytes";
    }
    const k = 1024;
    const dm = 2 < 0 ? 0 : 2;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
  }

  formateUptime(timestamp: number): string {
    const hours = Math.floor(timestamp / 60 / 60);
    const minutes = Math.floor(timestamp / 60) - (hours * 60);
    const seconds = timestamp % 60;
    return hours.toString().padStart(2, '0') + 'h' +
      minutes.toString().padStart(2, '0') + 'm' + seconds.toString().padStart(2, '0') + 's';
  }

  // every 1 second call this function
  updateTime(): void {
    setInterval(() => {
      this.processUptime = this.formateUptime(this.timeStamp + 1);
      this.timeStamp++;
    }, 1000);
  }

  private formatDate(date: Date): string {
    const dd = date.getDate();
    const mm = date.getMonth() + 1;
    const year = date.getFullYear();
    if (dd < 10) {
      const day = `0${dd}`;
    }
    if (mm < 10) {
      const month = `0${mm}`;
    }
    return `${mm}/${dd}/${year}`;
  }


}

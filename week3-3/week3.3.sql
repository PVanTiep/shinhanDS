-- bai 1.1
CREATE OR REPLACE PROCEDURE dept_info(id number)
          IS
                r_depart departments%rowtype; 
          BEGIN
                 select * into r_depart from departments where 1=1 and departments.department_id= id;
                 DBMS_OUTPUT.PUT_LINE( 'id: '||r_depart.department_id);
                 DBMS_OUTPUT.PUT_LINE( 'name: '||r_depart.department_name);
                 DBMS_OUTPUT.PUT_LINE( 'manager_id: '||r_depart.manager_id);
                 DBMS_OUTPUT.PUT_LINE( 'location_id: '||r_depart.location_id) ;  
          EXCEPTION
                 when no_data_found then
                 DBMS_OUTPUT.PUT_LINE('No data found');  
          END;
          begin
                 dept_info(10);
          end;
--bai 1.2          
CREATE OR REPLACE PROCEDURE add_job(job_id varchar2, job_name varchar2)
          IS
          BEGIN
                insert into jobs values (job_id, job_name, null, null);
          EXCEPTION
                when dup_val_on_index then
                        rollback;
                        DBMS_OUTPUT.PUT_LINE('inserts have been rolled back');  
          END;
          
          drop procedure add_job;
          
          begin
                 add_job('STR','STREAMER');
          end;
--bai 1.3          
CREATE OR REPLACE PROCEDURE update_comm(emp_id number)
          IS         
                c_number number;
          BEGIN
                SELECT COMMISSION_PCT into c_number from employees where employees.employee_id = emp_id;
                if c_number is null then c_number:=0; end if;
                c_number := c_number *1.05;
                UPDATE employees
                SET COMMISSION_PCT = c_number
                WHERE employee_id = emp_id;
                DBMS_OUTPUT.PUT_LINE('Update successful');          
          EXCEPTION
                 when no_data_found then
                 DBMS_OUTPUT.PUT_LINE('No data found');            
          END;
              
          begin
                update_comm(135);
          end;

--bai 1.4
CREATE OR REPLACE PROCEDURE add_emp(emp_id number, emp_fn varchar2, emp_lsname varchar2, emp_mail varchar2, emp_phone varchar2, 
                                                            emp_hire_date date, emp_job_ib varchar2, emp_salary number,emp_comm number ,emp_ma_id number, emp_depart_id number )
          IS
          BEGIN
                insert into employees values(emp_id, emp_fn, emp_lsname, emp_mail, emp_phone, 
                                                            emp_hire_date, emp_job_ib, emp_salary, emp_comm ,emp_ma_id, emp_depart_id);
          EXCEPTION
                when dup_val_on_index then
                        rollback;
                        DBMS_OUTPUT.PUT_LINE('inserts have been rolled back');            
          END;
      
          begin 
                add_emp(99,'Tiep','Phan Van', '123@@gmail.com', '1231123123',TO_date('30-SEP-2022'), 'PU_MAN', null, null, null, null);

                --add_emp(99,'Tiep','Phan Van', '123@@gmail.com', '1231123123',TO_date('30-SEP-2023'), 'PU_MAN', null, null, null, null);
                
          end;
delete from employees where employees.employee_id=99;
--bai 1.5        
CREATE OR REPLACE PROCEDURE delete_emp(emp_id number)
          IS
          BEGIN
                delete from employees where employees.employee_id = emp_id;
          --EXCEPTION
          END;
      
          begin 
                delete_emp(99);
          end;

-- bai 1.6 
CREATE OR REPLACE PROCEDURE find_emp
          IS
                cursor get_inf is
                select emp.employee_id, emp.first_name from employees emp, jobs jb
                where emp.job_id= jb.job_id and (emp.salary between jb.min_salary and jb.max_salary);
          BEGIN
          for r_inf in get_inf 
          loop
                DBMS_OUTPUT.PUT_LINE(r_inf.employee_id ||' - '||r_inf.first_name);                        
          end loop;
          EXCEPTION
                 when no_data_found then
                        DBMS_OUTPUT.PUT_LINE('No data found');            
          END;
          begin
                find_emp;
          end;    
--bai 1.7
CREATE OR REPLACE PROCEDURE update_comm
          IS
          c_number number;
          f_number number(8,2);
          cursor get_inf is
          select employee_id, salary, hire_date  from employees ;
          BEGIN
          for r_emp in get_inf 
          loop
                c_number :=r_emp.salary;
                f_number := (months_between(trunc(sysdate), trunc(r_emp.hire_date)) /12) ;
                
                if f_number >=2 then c_number:=c_number+200;
                elsif (f_number <2) and (f_number >=1) then c_number:= c_number+100;
                else c_number := c_number+50;
                end if;
                
                UPDATE employees
                SET salary = c_number
                WHERE employee_id=r_emp.employee_id;
          end loop;
                DBMS_OUTPUT.PUT_LINE('Update successfull');
       --EXCEPTION
          END;
        
          declare
          c_number number(8,2);
          begin
                update_comm;
                --c_number:= (months_between(date '2014-10-10', date '2013-7-10') )/12;
               -- DBMS_OUTPUT.PUT_LINE(c_number);
          end;
--bai 1.8
CREATE OR REPLACE PROCEDURE job_his(emp_id number)
          IS
                cursor get_inf is
                select start_date, end_date  from job_history where employee_id = emp_id ;
                
          BEGIN
                  for r_job_his in get_inf 
                  loop
                        DBMS_OUTPUT.PUT_LINE('start_date: ' || r_job_his.start_date || ' - end_date: '|| r_job_his.end_date );
        
                  end loop;
          EXCEPTION
                 when no_data_found then
                        DBMS_OUTPUT.PUT_LINE('No data found');  
          END;

          begin
                job_his(101);
          end;

--bai 2.1
CREATE OR REPLACE FUNCTION sum_salary
          (id number)
          RETURN number
          IS
                total number :=0;
          BEGIN
              select sum(salary) into total 
                    from employees 
                            where employees.department_id=id;
              if total is null then return 0;
                ELSE return total;
              end if;
          END;
         -- drop function sum_salary;
         begin
            --dept_info(1);
            DBMS_OUTPUT.PUT_LINE(' sum_salary = ' || sum_salary(90));
         end;

--bai 2.2
CREATE OR REPLACE FUNCTION name_con(ct_id varchar2)
          RETURN varchar2
          IS
                name_ct varchar2(100);
          BEGIN
                select country_name into name_ct from countries where country_id=ct_id;
                return name_ct; 
          EXCEPTION
                 when no_data_found then
                        DBMS_OUTPUT.PUT_LINE('No data found');            
          END;
          
          begin
                DBMS_OUTPUT.PUT_LINE(name_con('AU'));
          end;      
--bai 2.3          
CREATE OR REPLACE FUNCTION annual_comp(mounth_salary number, comm number)
          RETURN number 
          IS 
                year_salary number;
          BEGIN
                year_salary:= mounth_salary*12 + (mounth_salary*comm);
                return year_salary;
          --EXCEPTION
          END;
          
          begin
                DBMS_OUTPUT.PUT_LINE(annual_comp(100,0.3));
          end; 

--bai 2.4
CREATE OR REPLACE FUNCTION avg_salary(depart_id number)
          RETURN number
          IS
                avg_sala number(8,2);
          BEGIN
                select sum(salary) into avg_sala from employees where department_id=depart_id;
                avg_sala:= avg_sala/12;
                if avg_sala is null then avg_sala:=0; end if;
                return avg_sala;           
          END;
          begin
                DBMS_OUTPUT.PUT_LINE(avg_salary(90));
          end;                 
          
--bai 2.5
CREATE OR REPLACE FUNCTION time_work(emp_id number)
          RETURN number
          IS
                c_number number(8,0);
          BEGIN
                select  months_between(trunc(sysdate), trunc(hire_date)) into c_number from employees where employee_id=emp_id;
                return c_number;
          EXCEPTION
                 when no_data_found then
                        DBMS_OUTPUT.PUT_LINE('No data found');                      
          END;
          
          begin
                DBMS_OUTPUT.PUT_LINE(time_work(100));
          end;         
          
--bai 3.1
CREATE OR REPLACE TRIGGER date_hire_emp
          BEFORE  INSERT or UPDATE
          ON employees 
          FOR EACH ROW
          DECLARE
          BEGIN
          if trunc(:new.hire_date) > trunc(sysdate) then 
                RAISE_APPLICATION_ERROR( -20001, 'ngay thue phai lon be hon hoac bang ngay hien tai' ); 
                else DBMS_OUTPUT.PUT_LINE('successful');
          end if;
          END;          
          begin 
                add_emp(99,'Tiep','Phan Van', '123@@gmail.com', '1231123123',TO_date('30-SEP-2022'), 'PU_MAN', null, null, null, null);

                --add_emp(99,'Tiep','Phan Van', '123@@gmail.com', '1231123123',TO_date('30-SEP-2023'), 'PU_MAN', null, null, null, null);
                
          end;
          
--bai 3.2
CREATE OR REPLACE TRIGGER min_max_sala_job
          BEFORE INSERT  or UPDATE
          ON jobs 
          FOR EACH ROW
          DECLARE
          BEGIN
                if :new.min_salary>= :new.max_salary then 
                RAISE_APPLICATION_ERROR( -20001, 'luong thap nhat phai be hon hoac bang luong cao nhat' ); 
                else DBMS_OUTPUT.PUT_LINE('successful');
                end if;
          --EXCEPTION     
          END;          
                insert into jobs values ('str', 'streamer',1020,500);
                
--bai 3.3                                
CREATE OR REPLACE TRIGGER start_end_his_job
          BEFORE INSERT  or UPDATE
          ON job_history 
          FOR EACH ROW
          DECLARE
          BEGIN
                if :new.start_date> :new.end_date then 
                RAISE_APPLICATION_ERROR( -20001, 'ngay bat dau phai nho hon ngay ket thuc' ); 
                else DBMS_OUTPUT.PUT_LINE('successful');
                end if;
          --EXCEPTION     
          END;          
                insert into job_history values (102, TO_date('30-SEP-2014'),TO_date('30-SEP-2013'),'IT_PROG',60);          
                
-- bai 3.4                
CREATE OR REPLACE TRIGGER salary_comm
          BEFORE UPDATE
          ON employees 
          FOR EACH ROW
          DECLARE
          BEGIN
          if (:new.salary<:old.salary) or (:new.commission_pct< :old.commission_pct) then
          RAISE_APPLICATION_ERROR( -20001, 'luong va hoa hong phai lon hon muc luong, hoa hong cu' ); 
          else DBMS_OUTPUT.PUT_LINE('successful');
          end if;          
          --EXCEPTION     
          END;
                

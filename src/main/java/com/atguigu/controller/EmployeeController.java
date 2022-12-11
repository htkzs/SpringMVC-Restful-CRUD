package com.atguigu.controller;

import com.atguigu.dao.DepartmentDao;
import com.atguigu.dao.EmployeeDao;
import com.atguigu.entity.Department;
import com.atguigu.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentDao departmentDao;
    /*
     * @Author GhostGalaxy
     * @Description 查询所有员工的信息
     * @Date 11:03:24 2022/12/10
     * @Param [model]
     * @return java.lang.String
     **/
    @RequestMapping("/emps")
    public String queryAllEmployees(Model model){
        Collection<Employee> all = employeeDao.getAll();
        //相当与放入request域中 携带到list.jsp页面
        model.addAttribute("employees",all);
        return "list";
    }
    /*
     * @Author GhostGalaxy
     * @Description 来到员工页面首先查询员工的部门信息并作展示
     * @Date 11:04:21 2022/12/10
     * @Param [model]
     * @return java.lang.String
     **/
    @RequestMapping("/toAddPage")
    public String toAddPage(Model model){
        //查询所有部门信息作展示
        Collection<Department> departments = departmentDao.getDepartments();
        //将查询的结果放入到request域中，返回到页面
        model.addAttribute("deptItems",departments);
        model.addAttribute("employee",new Employee(1001,"E-AA","aa@163.com",1,new Department(101,"技术部")));
        //跳转到添加员工的页面
        return "add";
    }
    /*
     * @Author GhostGalaxy
     * @Description 增加一个员工 处理POST请求
     * @Date 15:42:07 2022/12/10
     * @Param [employee]
     * @return java.lang.String
     **/
    @RequestMapping(value="/emp",method = RequestMethod.POST)
    public String addEmployee(@Valid Employee employee,BindingResult result,Model model){
        System.out.println("需要增加的员工信息为"+employee);
        List<ObjectError> errors = result.getAllErrors();
        System.out.println(errors);
        boolean res = result.hasErrors();
        Collection<Department> departments = departmentDao.getDepartments();
        //将查询的结果放入到request域中，返回到页面
        model.addAttribute("deptItems",departments);
        System.out.println("是否有错误发生"+res);
        Map<String,String> map = new HashMap<String,String>();
        if(result.hasErrors()){
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error:fieldErrors){
                System.out.println("错误的消息为："+error.getDefaultMessage());
                System.out.println("错误的字段为："+error.getField());
                map.put(error.getField(),error.getDefaultMessage());
                System.out.println("========================");
            }
            model.addAttribute("errors",map);
            //如果数据校验不正确就跳回到添加页面
            return "add";
        }else{
            employeeDao.save(employee);
            //重定向到查询所有员工的方法中
            return "redirect:emps";
        }
    }
    /*
     * @Author GhostGalaxy
     * @Description 去到员工修改的页面 通过超链接访问
     * @Date 16:07:12 2022/12/10
     * @Param [id]
     * @return java.lang.String
     **/
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    public String editEmployee(@PathVariable("id") Integer id,Model model){
        //来到员工的修改页面，先回显当前修改员工的信息
        Employee employee = employeeDao.get(id);
        //使用SpringMVC表单标签作数据回显
        model.addAttribute("employee",employee);
        //还需要回显部门的名称下拉列表
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("deptItems",departments);
        return "edit";
    }
    /*
     * @Author GhostGalaxy
     * @Description 员工修改提交操作 使用restful模拟PUT请求
     * @Date 16:57:13 2022/12/10
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping(value = "emp/{id}",method = RequestMethod.PUT)
    public String updateEmployee(@PathVariable("id") Integer id,@ModelAttribute("employee") Employee employee){
        System.out.println("要修改的员工的信息为"+ employee+"=========>id的值为"+id);
        employeeDao.save(employee);
        return "redirect:/emps";
    }
    /*
     * @Author GhostGalaxy
     * @Description 执行目标方法之前，先执行ModelAttribute，防止全字段更新丢失数据@RequestParam 相当于request.getParameter();
     * @Date 17:16:56 2022/12/10
     * @Param [id, model]
     * @return void
     **/
    @ModelAttribute
    public void myModelAttribute(@RequestParam(value = "id",required = false) Integer id,Model model){
        if(id != null){
            //查询员工的全字段信息
            Employee employee = employeeDao.get(id);
            //将查询的全字段信息放到隐含模型中
            model.addAttribute("employee",employee);

        }

    }
    /*
     * @Author GhostGalaxy
     * @Description 使用Post请求模拟delete操作 删除员工
     * @Date 17:54:58 2022/12/10
     * @Param [id]
     * @return java.lang.String
     **/
    @RequestMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}

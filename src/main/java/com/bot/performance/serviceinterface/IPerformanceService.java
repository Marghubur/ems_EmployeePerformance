package com.bot.performance.serviceinterface;

import com.bot.performance.model.*;
import org.javatuples.Pair;
import reactor.util.function.Tuple2;

import java.util.List;

public interface IPerformanceService {
    List<EmployeePerformance> GetAllEmpPerformanceService();
    Pair<List<PerfomanceObjective>, List<EmployeeRole>> ObjectiveInsertUpdateService(PerfomanceObjective objectiveDetail) throws Exception;
    EmployeePerformance UpdateEmployeeObjectiveService(EmployeePerformance employeePerformance) throws Exception;
    List<PerfomanceObjective> GetEmployeeObjectiveService(int designationId, int companyId, long employeeId) throws Exception;
    Pair<List<PerfomanceObjective>, List<EmployeeRole>> GetPerformanceObjectiveService(FilterModel filterModel) throws Exception;
    List<?> getEmployeeByManagerId(long managerId);
}

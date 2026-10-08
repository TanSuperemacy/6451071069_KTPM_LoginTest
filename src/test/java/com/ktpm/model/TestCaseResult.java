package com.ktpm.model;

/**
 * Model lưu trữ thông tin kết quả từng test case để xuất ra file Excel
 */
public class TestCaseResult {
    private String testCaseId;      // Ví dụ: TC01
    private String testName;        // Tên kịch bản kiểm thử
    private String inputData;       // Dữ liệu đầu vào (username, password)
    private String expectedResult;  // Kết quả mong đợi
    private String actualResult;    // Kết quả thực tế
    private String status;          // PASS hoặc FAIL
    private String executionTime;   // Thời gian thực thi

    public TestCaseResult(String testCaseId, String testName, String inputData, 
                          String expectedResult, String actualResult, String status, String executionTime) {
        this.testCaseId = testCaseId;
        this.testName = testName;
        this.inputData = inputData;
        this.expectedResult = expectedResult;
        this.actualResult = actualResult;
        this.status = status;
        this.executionTime = executionTime;
    }

    public String getTestCaseId() { return testCaseId; }
    public String getTestName() { return testName; }
    public String getInputData() { return inputData; }
    public String getExpectedResult() { return expectedResult; }
    public String getActualResult() { return actualResult; }
    public String getStatus() { return status; }
    public String getExecutionTime() { return executionTime; }
}

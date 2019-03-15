package com.hdnz.inanming.bean.result;

import java.util.ArrayList;
import java.util.List;

public class TaskBean {

    /**
     * upcomingTasks : 3
     * overdue : 65
     * carryOut : 9
     * total : 4
     */

    private String upcomingTasks;
    private String overdue;
    private String carryOut;
    private String total;
    private List<ListBean> list;
    /**
     * totalCount : 2
     * pageSize : 10
     * totalPage : 1
     * currPage : 1
     */

    private int totalCount;
    private int pageSize;
    private int totalPage;
    private int currPage;

    public String getUpcomingTasks() {
        return upcomingTasks;
    }

    public void setUpcomingTasks(String upcomingTasks) {
        this.upcomingTasks = upcomingTasks;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public String getCarryOut() {
        return carryOut;
    }

    public void setCarryOut(String carryOut) {
        this.carryOut = carryOut;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }


    public static class ListBean {
        /**
         * id : 326776099831156736
         * applyName : 3
         * applyAdr : mixed
         * type : 3
         * agentflag : 0
         * lastTime : mixed
         * overdue : 0:未逾期1:已逾期
         * status : 1:未分配,2,6,7:都是已分配
         */

        private String id;
        private String applyName;
        private String applyAdr;
        private String type;
        private int agentflag;
        private String lastTime;
        private int overdue;
        private int status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getApplyName() {
            return applyName;
        }

        public void setApplyName(String applyName) {
            this.applyName = applyName;
        }

        public String getApplyAdr() {
            return applyAdr;
        }

        public void setApplyAdr(String applyAdr) {
            this.applyAdr = applyAdr;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getAgentflag() {
            return agentflag;
        }

        public void setAgentflag(int agentflag) {
            this.agentflag = agentflag;
        }

        public String getLastTime() {
            return lastTime;
        }

        public void setLastTime(String lastTime) {
            this.lastTime = lastTime;
        }

        public int getOverdue() {
            return overdue;
        }

        public void setOverdue(int overdue) {
            this.overdue = overdue;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}

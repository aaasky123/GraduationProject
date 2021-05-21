package com.wxj.work.entity;

import java.util.ArrayList;
import java.util.List;

public class HelperExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HelperExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andHelperUserIdIsNull() {
            addCriterion("helper_user_id is null");
            return (Criteria) this;
        }

        public Criteria andHelperUserIdIsNotNull() {
            addCriterion("helper_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andHelperUserIdEqualTo(Long value) {
            addCriterion("helper_user_id =", value, "helperUserId");
            return (Criteria) this;
        }

        public Criteria andHelperUserIdNotEqualTo(Long value) {
            addCriterion("helper_user_id <>", value, "helperUserId");
            return (Criteria) this;
        }

        public Criteria andHelperUserIdGreaterThan(Long value) {
            addCriterion("helper_user_id >", value, "helperUserId");
            return (Criteria) this;
        }

        public Criteria andHelperUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("helper_user_id >=", value, "helperUserId");
            return (Criteria) this;
        }

        public Criteria andHelperUserIdLessThan(Long value) {
            addCriterion("helper_user_id <", value, "helperUserId");
            return (Criteria) this;
        }

        public Criteria andHelperUserIdLessThanOrEqualTo(Long value) {
            addCriterion("helper_user_id <=", value, "helperUserId");
            return (Criteria) this;
        }

        public Criteria andHelperUserIdIn(List<Long> values) {
            addCriterion("helper_user_id in", values, "helperUserId");
            return (Criteria) this;
        }

        public Criteria andHelperUserIdNotIn(List<Long> values) {
            addCriterion("helper_user_id not in", values, "helperUserId");
            return (Criteria) this;
        }

        public Criteria andHelperUserIdBetween(Long value1, Long value2) {
            addCriterion("helper_user_id between", value1, value2, "helperUserId");
            return (Criteria) this;
        }

        public Criteria andHelperUserIdNotBetween(Long value1, Long value2) {
            addCriterion("helper_user_id not between", value1, value2, "helperUserId");
            return (Criteria) this;
        }

        public Criteria andWorkIdIsNull() {
            addCriterion("work_id is null");
            return (Criteria) this;
        }

        public Criteria andWorkIdIsNotNull() {
            addCriterion("work_id is not null");
            return (Criteria) this;
        }

        public Criteria andWorkIdEqualTo(Long value) {
            addCriterion("work_id =", value, "workId");
            return (Criteria) this;
        }

        public Criteria andWorkIdNotEqualTo(Long value) {
            addCriterion("work_id <>", value, "workId");
            return (Criteria) this;
        }

        public Criteria andWorkIdGreaterThan(Long value) {
            addCriterion("work_id >", value, "workId");
            return (Criteria) this;
        }

        public Criteria andWorkIdGreaterThanOrEqualTo(Long value) {
            addCriterion("work_id >=", value, "workId");
            return (Criteria) this;
        }

        public Criteria andWorkIdLessThan(Long value) {
            addCriterion("work_id <", value, "workId");
            return (Criteria) this;
        }

        public Criteria andWorkIdLessThanOrEqualTo(Long value) {
            addCriterion("work_id <=", value, "workId");
            return (Criteria) this;
        }

        public Criteria andWorkIdIn(List<Long> values) {
            addCriterion("work_id in", values, "workId");
            return (Criteria) this;
        }

        public Criteria andWorkIdNotIn(List<Long> values) {
            addCriterion("work_id not in", values, "workId");
            return (Criteria) this;
        }

        public Criteria andWorkIdBetween(Long value1, Long value2) {
            addCriterion("work_id between", value1, value2, "workId");
            return (Criteria) this;
        }

        public Criteria andWorkIdNotBetween(Long value1, Long value2) {
            addCriterion("work_id not between", value1, value2, "workId");
            return (Criteria) this;
        }

        public Criteria andWorkFlowIdIsNull() {
            addCriterion("work_flow_id is null");
            return (Criteria) this;
        }

        public Criteria andWorkFlowIdIsNotNull() {
            addCriterion("work_flow_id is not null");
            return (Criteria) this;
        }

        public Criteria andWorkFlowIdEqualTo(Long value) {
            addCriterion("work_flow_id =", value, "workFlowId");
            return (Criteria) this;
        }

        public Criteria andWorkFlowIdNotEqualTo(Long value) {
            addCriterion("work_flow_id <>", value, "workFlowId");
            return (Criteria) this;
        }

        public Criteria andWorkFlowIdGreaterThan(Long value) {
            addCriterion("work_flow_id >", value, "workFlowId");
            return (Criteria) this;
        }

        public Criteria andWorkFlowIdGreaterThanOrEqualTo(Long value) {
            addCriterion("work_flow_id >=", value, "workFlowId");
            return (Criteria) this;
        }

        public Criteria andWorkFlowIdLessThan(Long value) {
            addCriterion("work_flow_id <", value, "workFlowId");
            return (Criteria) this;
        }

        public Criteria andWorkFlowIdLessThanOrEqualTo(Long value) {
            addCriterion("work_flow_id <=", value, "workFlowId");
            return (Criteria) this;
        }

        public Criteria andWorkFlowIdIn(List<Long> values) {
            addCriterion("work_flow_id in", values, "workFlowId");
            return (Criteria) this;
        }

        public Criteria andWorkFlowIdNotIn(List<Long> values) {
            addCriterion("work_flow_id not in", values, "workFlowId");
            return (Criteria) this;
        }

        public Criteria andWorkFlowIdBetween(Long value1, Long value2) {
            addCriterion("work_flow_id between", value1, value2, "workFlowId");
            return (Criteria) this;
        }

        public Criteria andWorkFlowIdNotBetween(Long value1, Long value2) {
            addCriterion("work_flow_id not between", value1, value2, "workFlowId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNull() {
            addCriterion("company_id is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNotNull() {
            addCriterion("company_id is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdEqualTo(Integer value) {
            addCriterion("company_id =", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotEqualTo(Integer value) {
            addCriterion("company_id <>", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThan(Integer value) {
            addCriterion("company_id >", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("company_id >=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThan(Integer value) {
            addCriterion("company_id <", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThanOrEqualTo(Integer value) {
            addCriterion("company_id <=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIn(List<Integer> values) {
            addCriterion("company_id in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotIn(List<Integer> values) {
            addCriterion("company_id not in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdBetween(Integer value1, Integer value2) {
            addCriterion("company_id between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("company_id not between", value1, value2, "companyId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
package com.example.settingtest.repository;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public abstract class AbstractMybatisRepository {

	private Logger log = LogManager.getLogger(this.getClass());

    public SqlSessionFactory sqlSessionFactory;
    public String mapperPrefix;

    protected void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        throw new NotImplementedException();
    }

    protected void setMapperPrefix() {
        throw new NotImplementedException();
    }

    public int insert(String statement, Map<String, Object> params) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            int result = session.insert(mapperPrefix + statement, params);
            session.close();
            return result;
        } catch (Exception e) {
        	log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            	log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public int insert(String statement, Object params) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            int result = session.insert(mapperPrefix + statement, params);
            session.close();
            return result;
        } catch (Exception e) {
        	log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            	log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public int update(String statement, Map<String, Object> params) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            int result = session.update(mapperPrefix + statement, params);
            session.close();
            return result;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public int update(String statement, Object params) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            int result = session.update(mapperPrefix + statement, params);
            session.close();
            return result;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public <T> T selectOne(String statement, Map<String, Object> params) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            T result = session.selectOne(mapperPrefix + statement, params);
            session.close();
            return result;
        } catch (Exception e) {
        	log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            	log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public <T> T selectOne(String statement, Object params) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            T result = session.selectOne(mapperPrefix + statement, params);
            session.close();
            return result;
        } catch (Exception e) {
        	log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            	log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public <E> List<E> selectList(String statement, Map<String, Object> parameter) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            List<E> result = session.selectList(mapperPrefix + statement, parameter);
            session.close();
            return result;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public <T> T selectOne(String statement) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            T result = session.selectOne(mapperPrefix + statement);
            session.close();
            return result;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public <E> List<E> selectList(String statement) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            List<E> result = session.selectList(mapperPrefix + statement);
            session.close();
            return result;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            Map<K, V> result = session.selectMap(mapperPrefix + statement, mapKey);
            session.close();
            return result;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public <K, V> Map<K, V> selectMap(String statement, Map<String, Object> parameter, String mapKey) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            Map<K, V> result = session.selectMap(mapperPrefix + statement, parameter, mapKey);
            session.close();
            return result;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public int insert(String statement) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            int result = session.insert(mapperPrefix + statement);
            session.close();
            return result;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public int update(String statement) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            int result = session.update(mapperPrefix + statement);
            session.close();
            return result;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
//            log.error("{}", e.getStackTrace());
            session.close();
            throw e;
        }
    }

    public int delete(String statement) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            int result = session.delete(mapperPrefix + statement);
            session.close();
            return result;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public int delete(String statement, Map<String, Object> params) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            int result = session.delete(mapperPrefix + statement, params);
            session.close();
            return result;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public int delete(String statement, Object params) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            int result = session.delete(mapperPrefix + statement, params);
            session.close();
            return result;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public <T> T insertLastSelectId(String statement, Map<String, Object> params) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            session.insert(mapperPrefix + statement, params);
            T lastSelectedId = session.selectOne("com.marvrus.moon_app_api.repository.mapper.defaultMapper.selectLastInsertId");
            session.close();
            return lastSelectedId;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }

    public <T> T insertLastSelectId(String statement) {
        SqlSession session = this.sqlSessionFactory.openSession();
        try {
            session.insert(mapperPrefix + statement);
            T lastSelectedId = session.selectOne("com.marvrus.moon_app_api.repository.mapper.defaultMapper.selectLastInsertId");
            session.close();
            return lastSelectedId;
        } catch (Exception e) {
            log.error("DB execution error : {}", e.getMessage());
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                log.error("{}", stackTraceElement.toString());
            }
            session.close();
            throw e;
        }
    }
}

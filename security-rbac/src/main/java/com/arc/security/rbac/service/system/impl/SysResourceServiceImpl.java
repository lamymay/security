package com.arc.security.rbac.service.system.impl;

import com.arc.annotation.Note;
import com.arc.model.domain.system.SysResource;
import com.arc.security.rbac.service.system.SysResourceService;
import com.arc.security.rbac.mapper.system.SysResourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 叶超
 * @since 2019/1/30 17:33
 */
@Slf4j
@Service
@SuppressWarnings("unchecked")
public class SysResourceServiceImpl implements SysResourceService {


    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private SysResourceMapper resourceMapper;

    @Override
    public Long save(SysResource resource) {
        return resourceMapper.save(resource) == 1 ? resource.getId() : null;
    }

    @Override
    public int delete(Long id) {
        return resourceMapper.delete(id);
    }

    @Override
    public int update(SysResource resource) {
        return resourceMapper.update(resource);
    }

    @Override
    public SysResource get(Long id) {
        return null;
    }

    @Override
    public Page<SysResource> page() {

        PageImpl page = new PageImpl(list());
        log.debug("结果={}", page);
        log.debug("结果={}", page);
        return page;
    }

    @Override
    public List<SysResource> list() {
        return resourceMapper.list();
    }

    @Override
    public int deleteIdIn(Set<Long> ids) {
        return resourceMapper.deleteIdIn(ids);
    }

    /**
     * @return 扫描系统controller的url 路径，然后保存到数据库
     */
    @Override
    public Integer scanSysResourceByControllerAnnotation() {
        Integer result = 1;
        //1 清空已有资源
        //1 获取系统中的全部Controller& RestController

        //获取所有-- Controller  requestMappings
        Set<SysResource> resources = new HashSet<>();
//        List<SysResource> resourceList = new ArrayList<>();

        //获取所有的 贴有注解 Controller & RestController  bean   注意：RestController 是 Controller的子集
        Map<String, Object> beansWithAnnotation = getAllControllerInIoc();
        Collection<Object> beans = beansWithAnnotation.values();

        for (Object bean : beans) {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {

                //获取父类的地址
                String[] classPath = getClassPath(method);
                String pathOnClass = "";
                if (classPath != null) {
                    pathOnClass = classPath[0];
                }
//                System.out.println("类" + bean.getClass().getSimpleName() + "上标记的地址 " + pathOnClass);
//                String[] methodPaths = getMethodPath(method);
//                if (methodPaths != null && methodPaths.length > 0) {
//
//                    pathOnMethod = methodPaths[0];
//                }


                //资源的完整路径 前半段
                StringBuilder wholePath = new StringBuilder();
                wholePath.append(checkPath(pathOnClass));

                String pathOnMethod = "";
                SysResource resource = null;

                log.debug("是{}类的方法{}", method.getDeclaringClass().getSimpleName(), method.getName());
                System.out.println("? PostMapping " + method.isAnnotationPresent(PostMapping.class));
                System.out.println("? DeleteMapping " + method.isAnnotationPresent(DeleteMapping.class));
                System.out.println("? PutMapping " + method.isAnnotationPresent(PutMapping.class));
                System.out.println("? GetMapping " + method.isAnnotationPresent(GetMapping.class));
                System.out.println("? ????????? " + method.isAnnotationPresent(Note.class));
                System.out.println("? ????????? getDeclaredAnnotations" + Arrays.toString(method.getDeclaredAnnotations()));
                System.out.println("? ????????? getAnnotations" + Arrays.toString(method.getAnnotations()));


                //处理方法
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    String[] value = method.getAnnotation(RequestMapping.class).value();
                    System.out.println(Arrays.toString(value));
                    if (value != null && value.length > 0) {
                        pathOnMethod = value[0];
                    }
                    RequestMethod[] requestMethods = method.getAnnotation(RequestMapping.class).method();


                    System.out.println(Arrays.toString(requestMethods));
                    System.out.println();
                    StringBuilder requestMethodStringBuilder = new StringBuilder();
                    //第一种可能 如果开发者只在方法上贴了RequestMapping 而不指定 method，则表示8中方法都可以访问的，第二种可能  method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST}
                    if (requestMethods == null | requestMethods.length < 1) {
                        requestMethods = RequestMethod.values();
                    }
                    for (RequestMethod var : requestMethods) {
                        System.out.println(var);
                        requestMethodStringBuilder.append(var).append(",");
                    }
                    resource = new SysResource();
                    log.debug("------------------>类 {} 上标记的地址 {} 方法上标记的地址 {}\nHTTP请求方法为 {}  ", bean.getClass().getSimpleName(), pathOnClass, pathOnMethod, requestMethodStringBuilder.toString());

                    resource.setMethod(requestMethodStringBuilder.toString());

                } else if (method.isAnnotationPresent(GetMapping.class)) {
                    String[] value = method.getAnnotation(GetMapping.class).value();
                    if (value != null && value.length > 0) {
                        pathOnMethod = value[0];
                        resource = new SysResource();
                        resource.setMethod(RequestMethod.GET.toString());
                        System.out.println(method.getAnnotation(GetMapping.class).name());
                        System.out.println(Arrays.toString(method.getAnnotation(GetMapping.class).value()));
                        System.out.println(Arrays.toString(method.getAnnotation(GetMapping.class).params()));
                        System.out.println(Arrays.toString(method.getAnnotation(GetMapping.class).path()));
                        System.out.println(Arrays.toString(method.getAnnotation(GetMapping.class).produces()));
                    }

                } else if (method.isAnnotationPresent(PostMapping.class)) {
                    String[] value = method.getAnnotation(PostMapping.class).value();
                    if (value != null && value.length > 0) {
                        pathOnMethod = value[0];
                        resource = new SysResource();
                        resource.setMethod(RequestMethod.POST.toString());
                    }

                } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                    String[] value = method.getAnnotation(DeleteMapping.class).value();
                    if (value != null && value.length > 0) {
                        pathOnMethod = value[0];
                        resource = new SysResource();
                        resource.setMethod(RequestMethod.DELETE.toString());
                    }

                } else if (method.isAnnotationPresent(PutMapping.class)) {
                    String[] value = method.getAnnotation(PutMapping.class).value();
                    if (value != null && value.length > 0) {
                        pathOnMethod = value[0];
                        resource = new SysResource();
                        resource.setMethod(RequestMethod.PUT.toString());
                    }
                } else {
                    log.debug("------------------>其他方法类{} 上标记的地址 {} ", bean.getClass().getSimpleName(), pathOnClass);
                }

                //path1+path2
                wholePath.append(checkPath(pathOnMethod));
                if (resource != null) {
                    //resourceName&note
                    if (method.isAnnotationPresent(Note.class)) {
                        log.debug("类 {} 上标记的地址 {} ", bean.getClass().getSimpleName(), pathOnClass);

                        Note note = method.getAnnotation(Note.class);
                        System.out.println(null == note);
                        resource.setResourceName(method.getAnnotation(Note.class).value());
                        resource.setPriority(note.priority());
                    }

                    if (wholePath.toString().trim().length() > 0) {
                        resource.setPath(wholePath.toString());
                        resources.add(resource);
                    }
                }


            }
        }
        System.out.println(resources.size());
        System.out.println(resources.size());
        System.out.println(resources.size());
        int batch = compareAndSave(resources);
        return batch;
    }

    /**
     * 扫描资源比对后入库
     * 后端只做数据的录入，别的（层级维护）不做
     *
     * @param resources
     * @return
     */

    //入库的 删除的
    // 更新怎么做，需要考虑一下，用path来比对？相同的保留，不同的先清空后插入
    //以url 为标准，
    // 相同的要么更新要么不动，1、若扫描得到的新集合其注释名称&url=数据库中的，则保持数据库中的数据不动 ； 2、若两者仅仅url一样，则更新
    //不同的 1、数据库中有新的集合中没有，则删掉数据库中的； 2、若数据库没有新集合中有则插入新数据
    //一样的 url的 数据表示接口没有变，去比较注释变换了没有
    //saved 集合去掉相同的
    //一样的 url的 数据表示接口没有变，去比较注释变换了没有
    //新的插入
    //删除的
    //saved 集合去掉相同的
    //                            //若两者仅仅url一样，则更新，只做updateBatch
//                        //url 不一样，说明接口不一样了
    //log.debug("---------------------相同的资源  id={}，url={},note={}", dbResource.getId(), dbResource.getPath(), dbResource.getNote());
    //log.debug("---------------------相同的资源 url={},note={}", resource.getPath(), resource.getNote());//
    private int compareAndSave(Set<SysResource> resources) {
        //清空数据

        if (resources == null || resources.size() == 0) {
            resourceMapper.truncate("t_sys_resource");
            return 1;
        }

        int delete = 0, save = 0, update = 0;

        List<SysResource> dbResources = resourceMapper.list();

        Map map = getCompareResultMap(new HashSet<>(dbResources), resources);

        System.out.println(map);
        Set<Long> deleteIdSet = null;
        Set<SysResource> insertList = null;
        Set<SysResource> updateList = null;

        Object deleteValue = map.get("delete");
        Object insertValue = map.get("insert");
        Object updateValue = map.get("update");

        if (deleteValue != null) {
            deleteIdSet = (Set<Long>) deleteValue;
        }
        if (insertValue != null) {
            insertList = (Set<SysResource>) insertValue;
        }
        if (updateValue != null) {
            updateList = (Set<SysResource>) updateValue;
        }

        delete = deleteBatch(deleteIdSet);
        save = saveBatch(insertList);
        update = updateBatch(updateList);

        log.debug("数据saveBatch={},update={},delete={}", save, update, delete);
        return save;
    }

    private int deleteBatch(Set<Long> deleteIdSet) {
        int delete = 0;
        if (deleteIdSet != null && deleteIdSet.size() > 0) {
            delete = resourceMapper.deleteIdIn(deleteIdSet);
        }
        return delete;
    }

    private int updateBatch(Set<SysResource> resources) {
        int update = 0;
        if (resources != null && resources.size() >= 0) {
            for (SysResource resource : resources) {
                update += resourceMapper.update(resource);
            }

        }
        return update;
    }

    private int saveBatch(Set<SysResource> resources) {
        int saveBatch = 0;
        if (resources != null && resources.size() > 0) {
            saveBatch = resourceMapper.saveBatch(resources);
        }
        return saveBatch;
    }

    private void print(Object str) {
        log.debug("数据={}", str);
    }

    private static void printCollection(Collection list) {
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                System.out.println(next);
            }
        } else {
            System.out.println("null");
        }
    }


    /**
     * 返回值暂时用map简单做了封装，key设计待修正
     * map的key：insert/update/delete value对应三个list
     *
     * @param dbList
     * @param bList
     * @return
     */
    //1、只新增 2、不动 3、insert update delete 三个都有可能 4、全删
    public static Map getCompareResultMap(Set<SysResource> dbList, Set<SysResource> bList) {

        //结果缓存 最后返回
        Map<String, Object> map = new HashMap<>(4);
        Set<Long> deleteIdSet = new HashSet<>();
        //临时map缓存数据
        HashMap<String, SysResource> dbMap = new HashMap<>();
        Map<String, SysResource> receivedMap = new HashMap<>();
        Set<SysResource> updateList = new HashSet<>();
        Set<SysResource> insertList = new HashSet<>();

        //不动数据库，源数据为空/null 且 新数据为空/null，，数据不合法（正常是不会走该分支） 返回空，
        if ((dbList == null || dbList.size() == 0) && (bList == null || bList.size() == 0)) {
            return map;
        }
        //没有新数据，删除数据库中的全部数据
        if (bList == null || bList.size() == 0) {
            for (SysResource sysResource : dbList) {
                deleteIdSet.add(sysResource.getId());
            }
            map.put("delete", deleteIdSet);
            map.put("insert", insertList);
            map.put("update", updateList);
            return map;
        }

        if (dbList == null || dbList.size() == 0) {
            map.put("insert", bList);
            return map;
        }

        //两个都非空
        if (dbList != null && dbList.size() > 0 && bList != null && bList.size() > 0) {
            for (SysResource resource : bList) {
                receivedMap.put(resource.getPath(), resource);
            }

            //筛选出需要删除的 和 需要更新的
            for (SysResource dbResource : dbList) {
                dbMap.put(dbResource.getPath(), dbResource);

                SysResource resourceInReceived = receivedMap.get(dbResource.getPath());
                if (resourceInReceived != null) {
                    //相同的更新
                    resourceInReceived.setId(dbResource.getId());
                    //updateMap.put(resourceInReceived.getPath(), resourceInReceived);
                    updateList.add(resourceInReceived);
                } else {
                    //要删除的
                    deleteIdSet.add(dbResource.getId());
                }

            }

            //筛选出需要更新的
            if (updateList != null) {
                for (SysResource updateResource : updateList) {

                    String key = updateResource.getPath();
                    SysResource resourceInReceived = receivedMap.get(key);

                    //能拿出来表示是需要更新的
                    if (resourceInReceived != null) {
                        receivedMap.remove(key);
                    }
                }
            }
            //找到需要插入的
            insertList.addAll(receivedMap.values());
            map.put("delete", deleteIdSet);
            map.put("insert", insertList);
            map.put("update", updateList);
        }
        return map;
    }


    /**
     * 类上上有贴注解【RequestMapping】url
     *
     * @param method
     * @return
     */
    private String[] getClassPath(Method method) {
        Class<?> clz = method.getDeclaringClass();
        RequestMapping classAnnotationRequestMapping = clz.getAnnotation(RequestMapping.class);
        //类上有贴注解RequestMapping,目的获取贴在类上的一段地址
        if (classAnnotationRequestMapping != null) {
            String[] pathOnClass = classAnnotationRequestMapping.value();//预期 类上的地址  数组的[0] 可能 为null！！}
            if (pathOnClass != null) {
//                log.debug("类上有该{}注解RequestMapping的方法是 ={}，路径是{}", clz.getName(), method.getName(), Arrays.toString(pathOnClass));
                return pathOnClass;
            }
        } else {
//            log.debug("类{}上没有地址该方法是 ={}", clz.getName(), method.getName());
        }
        return null;
    }


    /**
     * 获取全部的controller
     *
     * @return
     */
    private Map<String, Object> getAllControllerInIoc() {
        Map<String, Object> controllerBeansWithAnnotation = applicationContext.getBeansWithAnnotation(Controller.class);
        return controllerBeansWithAnnotation;
    }


    /**
     * url+Method +className+methodName +其他
     *
     * @param method
     * @return
     */
    private String[] getMethodPath(Method method) {
        String classSimpleName = method.getDeclaringClass().getSimpleName();
//        log.debug("是{}类的方法{}", classSimpleName, method.getName());
        if (method.isAnnotationPresent(GetMapping.class)) {
            return method.getAnnotation(GetMapping.class).value();
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            return method.getAnnotation(PostMapping.class).value();
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            return method.getAnnotation(DeleteMapping.class).value();
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            return method.getAnnotation(PutMapping.class).value();
        } else {
            return null;
        }
    }


    public static void main(String[] args) {
        //两段url 做判断
        System.out.println(checkPath(null));
        System.out.println(checkPath(""));
        System.out.println(checkPath("   "));
        System.out.println("----------------------------------");
        System.out.println(checkPath("/"));
        System.out.println(checkPath("//"));
        System.out.println(checkPath("///"));
        System.out.println("----------------------------------");
        System.out.println(checkPath("//user"));
        System.out.println(checkPath("user//"));

        System.out.println(checkPath("user"));
        System.out.println(checkPath("/user"));
        System.out.println(checkPath("user/"));
        System.out.println(checkPath("/user/"));
        System.out.println("----------------------------------");
        System.out.println(checkPath("user//get/"));
        System.out.println(checkPath("user//save"));

    }

    /**
     * 校验中的斜线
     * 保证斜线开头，非斜线结尾，例外空串返回空串，
     * 去掉连续两个正斜线中的一个
     *
     * @param path
     * @return
     */
    public static String checkPath(String path) {
        if (path == null) return null;
        if ("/".equals(path)) return path;
        path = path.replace("//", "/");
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }
}


//                String wholeUrl = path1[0].toString() + path2[0].toString();//预计是完整路径
//                if (method.isAnnotationPresent(WebSysResource.class)) {
//                    WebSysResource annotation = method.getAnnotation(WebSysResource.class);
//                    if (annotation != null) {
//                        String realmName = annotation.resourceName();
//                        String note = annotation.note();
//                        Integer priority = annotation.priority();
//                        SysResourceType type = annotation.type();
//                        ParentKey parentKey = annotation.parentKey();//指明父级是什么
//                        ParentKey key = annotation.key();
//                        RequestMethod[] requestMethods = method.getAnnotation(RequestMapping.class).method();//被请求的方法是何种类型 请求方法的 数组 7种
//
//                        String packageName = method.getDeclaringClass().getPackage().getName();//预计是 packageName
//                        String[] path1 = method.getDeclaringClass().getAnnotation(RequestMapping.class).value();//预期 类上的地址  数组的[0] 可能 为null！！
//                        String[] path2 = method.getAnnotation(RequestMapping.class).value();//路径 但是怎么获取类上贴的那部分url呢？
//
//                        String wholeUrl = path1[0].toString() + path2[0].toString();//预计是完整路径
//                        if (!wholeUrl.startsWith("/")) {
//                            wholeUrl = "/" + wholeUrl;//补全url
//                        }
//
//                    }
//                }


//boolean getMappingAnnotation =method.isAnnotationPresent(GetMapping.class) ;
//                boolean postMappingAnnotation = method.isAnnotationPresent(PostMapping.class);
//                boolean putMappingAnnotation = method.isAnnotationPresent(PutMapping.class);
//                boolean deleteMappingAnnotation = method.isAnnotationPresent(DeleteMapping.class);
//                boolean patchMappingAnnotation = method.isAnnotationPresent(PatchMapping.class);
//
//                System.out.println("? GetMapping " + method.isAnnotationPresent(GetMapping.class));
//        System.out.println("? PostMapping " + method.isAnnotationPresent(PostMapping.class));
//        System.out.println("? DeleteMapping " + method.isAnnotationPresent(DeleteMapping.class));


//        Map<String, Object> restControllerBeansWithAnnotation = applicationContext.getBeansWithAnnotation(RestController.class);
//        Map<String, Object> beansWithAnnotation = new HashMap<>();
//        System.out.println("Controller" + controllerBeansWithAnnotation.size());
//        System.out.println("RestController " + restControllerBeansWithAnnotation.size());
//        log.debug("Controller ={},RestController ={}", controllerBeansWithAnnotation.size(), restControllerBeansWithAnnotation.size());
//        beansWithAnnotation.putAll(controllerBeansWithAnnotation);
//        beansWithAnnotation.putAll(restControllerBeansWithAnnotation);

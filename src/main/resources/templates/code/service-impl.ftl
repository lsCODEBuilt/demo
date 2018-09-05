
package ${basePackage}.serviceImpl;

import ${basePackage}.mapper.${modelNameUpperCamel}Mapper;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
import ${basePackage}.repository.${modelNameUpperCamel}Repository;
import ${basePackage}.common.config.AppConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
* @author ${AUTHOR}
* @create ${CREATE}
**/
@Slf4j
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl implements ${modelNameUpperCamel}Service {
    @Autowired
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

    @Autowired
    private ${modelNameUpperCamel}Repository ${modelNameLowerCamel}Repository;

    @Override
    public ${modelNameUpperCamel} save(${modelNameUpperCamel} ${modelNameLowerCamel}){
        return ${modelNameLowerCamel}Repository.save(${modelNameLowerCamel});
    }

    @Override
    public PageInfo<${modelNameUpperCamel}> findAll(int pageNo){
        PageHelper.startPage(pageNo, AppConfig.DEFAULT_PAGE_SIZE);
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Mapper.selectAll();
        return new PageInfo<>(list);
    }

    @Override
    public List<${modelNameUpperCamel}> findAll(){
        return ${modelNameLowerCamel}Repository.findAll();
    }

    @Override
    public void delete(String id){
        ${modelNameLowerCamel}Repository.delete(id);
    }

    @Override
    public int deleteByIds(String ids){
        return ${modelNameLowerCamel}Mapper.deleteByIds(ids);
    }

    @Override
    public ${modelNameUpperCamel} findById(String id){
        return ${modelNameLowerCamel}Repository.findOne(id);
    }
}
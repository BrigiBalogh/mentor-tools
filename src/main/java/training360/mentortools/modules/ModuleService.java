package training360.mentortools.modules;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training360.mentortools.NotFoundException;

import java.lang.reflect.Type;
import java.util.List;
@Service
@AllArgsConstructor
public class ModuleService {


    private final ModelMapper mapper;

    private final ModuleRepository moduleRepository;

    public List<ModuleDto> getModules() {
        Type targetListType = new TypeToken<List<ModuleDto>>() {
        }.getType();
        return mapper.map(moduleRepository.findAll(), targetListType);
    }


    public ModuleDto findModuleById(long id) {
        Module module = findModule(id);
        return mapper.map(module, ModuleDto.class);
    }

    public ModuleDto CreateModule(CreateModuleCommand command) {
        Module module = new Module(command.getTitle(), command.getUrl());
        moduleRepository.save(module);
        return mapper.map(module, ModuleDto.class);
    }

    @Transactional
    public ModuleDto updateModuleById(long id, UpdateModuleCommand command) {
        Module module = findModule(id);
        module.setTitle(command.getTitle());
        module.setUrl(command.getUrl());
        return mapper.map(module,ModuleDto.class);
    }

    public void deleteModule(long id) {
        moduleRepository.deleteById(id);
    }


    public Module findModule(long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }
}



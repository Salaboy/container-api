package org.kie.grid.services.endpoint.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.kie.grid.services.endpoint.api.KieGridService;
import org.kie.grid.services.endpoint.exception.BusinessException;
import org.kie.grid.services.endpoint.info.WhitePagesEntryInfo;
import org.kie.grid.services.endpoint.info.YellowPagesEntryInfo;
import org.kie.grid.spi.Grid;
import org.kie.grid.spi.Participant;
import org.kie.grid.spi.Service;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class KieGridServiceImpl implements KieGridService {

    @Context
    private SecurityContext context;

    @Inject
    private Grid grid;

    public KieGridServiceImpl() {
    }

    
    public void bootstrap(@Observes @Initialized(ApplicationScoped.class) Object init) {
        grid.bootstrap();
    }

    @Override
    public List<YellowPagesEntryInfo> getYellowPagesEntries() throws BusinessException {
        Map<String, String> entries = grid.getYellowPages().getAll();
        List<YellowPagesEntryInfo> infos = new ArrayList<>();
        for (String key : entries.keySet()) {
            infos.add(new YellowPagesEntryInfo(key, entries.get(key)));
        }
        return infos;
    }

    @Override
    public List<WhitePagesEntryInfo> getWhitePagesEntries() throws BusinessException {
        Map<String, Participant> entries = grid.getWhitePages().getAll();
        List<WhitePagesEntryInfo> infos = new ArrayList<>();
        for (String key : entries.keySet()) {
            infos.add(new WhitePagesEntryInfo(key, entries.get(key)));
        }
        return infos;

    }

    @Override
    public Participant newParticipant(Participant p) throws BusinessException {
        String id = UUID.randomUUID().toString();
        p.setId(id);
        grid.getWhitePages().register(p.getName(), p);
        return p;
    }
    
    
    @Override
    public Service newService(Service s) throws BusinessException {
        String id = UUID.randomUUID().toString();
        s.setId(id);
        grid.getYellowPages().register(s.getName(), s);
        return s;
    }

}

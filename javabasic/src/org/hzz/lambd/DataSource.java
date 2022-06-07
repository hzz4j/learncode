package org.hzz.lambd;

import java.util.List;

@FunctionalInterface
public interface DataSource {
    List<String> getData(Client client);
}

package com.davutozcan.bookmarkreader.summary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import difflib.Chunk;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

/**
 * Created by davut on 12/31/2017.
 */

public class DiffUtilImp
{
    List<String> original;
    List<String> neww;

    public DiffUtilImp(List<String> original, List<String> neww) {
        this.original = original;
        this.neww = neww;
    }

    List<Chunk> newLines()
    {
        try {
            return getChunksByType(Delta.TYPE.INSERT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Chunk> getChunksByType(Delta.TYPE type) throws IOException {
        final List<Chunk> listOfChanges = new ArrayList<Chunk>();
        final List<Delta> deltas = getDeltas();
        for (Delta delta : deltas) {
            if (delta.getType() == type) {
                listOfChanges.add(delta.getRevised());
            }
        }
        return listOfChanges;
    }


    private List<Delta> getDeltas() throws IOException {
        final Patch patch = DiffUtils.diff(original, neww);
        return patch.getDeltas();
    }


}

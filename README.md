What is VerticalSectionRecyclerView
===================================

Example of creating a RecyclerView that is automatically divided into sections(header and item) using a two-dimensional ArrayList.

Preview
-------

<p><img src="http://drive.google.com/uc?export=view&id=16pGlks-OPnHrYc9zwBGRfjGDRDIcf2x2" width="250" height="435"><p/>

<br/>

How to use
----------

### 1. Extends SectionRecyclerViewAdapter.

```java
  public class MainAdapter extends SectionRecyclerViewAdapter<Section> {
     ...
  }
```

### 2. Override Methods.

```java
@Override
    public int getSectionHeaderCount() {
      // Returns the number of headers to generate.
        return getItems().size();
    }

    @Override
    public int getSectionItemCount(int section) {
      // Returns the number of items contained in the header.
        return getItems().get(section).items.size();
    }

    @Override
    RecyclerView.ViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
      // Create and return a ViewHolder corresponding to the header.
      View view = getInflater().inflate(R.layout.listview_main_section, parent, false);
      return new HeaderViewHolder(view);
    }

    @Override
    void onBindSectionHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
      // Bind HeaderViewHolder.
    }

    @Override
    RecyclerView.ViewHolder onCreateSectionItemViewHolder(ViewGroup parent, int viewType) {
        // Create and return a ViewHolder corresponding to the item.
        View view = getInflater().inflate(R.layout.listview_main_section_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    void onBindSectionItemViewHolder(RecyclerView.ViewHolder holder, int position) {
      // Bind ItemViewHolder.
    }
```

### 3. Provide Methods.

```java
// Returns the number of headers (sections).
int headerCount = mAdapter.getSectionHeaderCount();

// Returns the number of items contained in the section.
int sectionItemCount = mAdapter.getSectionItemCount(int section);      

// Returns the number of the section corresponding to the adapterPosition.                  
int sectionPosition = mAdapter.getSectionPosition(int adapterPosition);

// Returns the adapterPosition where the header of this section is located.
int sectionHeaderPosition = mAdapter.getSectionHeaderPosition(int sectionPosition);

// In the section containing the item corresponding to the adapterPosition, returns the position at which position
int sectionItemPosition = mAdapter.getSectionItemPosition(int adapterPosition);

// Refreshes the items (including headers) contained in that section.
mAdapter.notifySectionChanged(int section);
```

### 4. Other implementation methods are the same as existing RecyclerView.
